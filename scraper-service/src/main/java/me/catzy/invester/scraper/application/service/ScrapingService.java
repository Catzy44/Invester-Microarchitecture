package me.catzy.invester.scraper.application.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.benmanes.caffeine.cache.Cache;

import me.catzy.invester.kafka.messages.RawArticleMessage;
import me.catzy.invester.scraper.application.factory.RawArticleFactory;
import me.catzy.invester.scraper.config.SourcesConfig;
import me.catzy.invester.scraper.infrastructure.messaging.kafka.RawArticlePublisher;
import me.catzy.invester.scraper.infrastructure.scraping.parser.ArticleParser;
import me.catzy.invester.scraper.infrastructure.scraping.selenium.WebDriverService;
import me.catzy.invester.scraper.util.Utils;

@Service
public class ScrapingService {
	private static final Logger logger = LoggerFactory.getLogger(ScrapingService.class);
	
	@Autowired private WebDriverService serviceWeb;
	@Autowired private RawArticlePublisher articleProd;
	@Autowired private RawArticleFactory articleFactory;
	@Autowired private Cache<String, Boolean> urlCache;
	@Autowired private SourcesConfig sourcesConfig;
	
	
	//initial delat zmienić na 1 z powrotem
	@Scheduled(fixedRate = 10, initialDelay = 0, timeUnit = TimeUnit.MINUTES)
	public void checkForAnyNews() throws MalformedURLException, Exception {
		logger.info("checking for news...");

		for(String s : sourcesConfig.getSources()) {
			try {
				processRSS(new URI(s).toURL());
			} catch (Exception e) {
				logger.error("failed processing one of privided RSS's");
				e.printStackTrace();
			}
		}
		serviceWeb.close();
	}
	
	public void processRSS(URL url) throws Exception {
		NodeList items = ArticleParser.loadDoc(url);
        
        List<RawArticleMessage> articles = new ArrayList<RawArticleMessage>();

        for (int i = 0; i < items.getLength(); i++) {
        	if(!(items.item(i) instanceof Element)) {
        		continue;
        	}
            Element item = (Element) items.item(i);
	        articles.add(articleFactory.fromRssElement(item));
        }
        
        for(RawArticleMessage article : articles) {
        	if(urlCache.getIfPresent(article.getUrl()) != null) {
        		continue;
        	}
        	urlCache.put(article.getUrl(), true);
        	
        	article.content = scrapeArticleContent(article);
        	
        	if(article.content == null || article.content.length() == 0) {
        		logger.error("Failed to scrape article: " + article.getUrl());
        		continue;
        	}//
        	
        	articleProd.produce(article);
        }
	}
	
	private String scrapeArticleContent(RawArticleMessage a) {
		try {
			WebDriver driver = serviceWeb.get();

			driver.get(a.url);

			// 1
			List<WebElement> el = driver.findElements(By.id("article"));

			// 2
			if (el.size() == 0) {
				logger.warn("article web-element not found - trying alternative method, URL:" + a.getUrl());
				el = driver.findElements(By.className("articlePage"));
			}

			// 3
			if (el.size() == 0) {
				logger.warn("article web-element not found AGAIN - trying alternative method (FX), URL:" + a.getUrl());
				el = driver.findElements(By.className("fxs_article_content"));
			}
			
			return Utils.extractArticleText(el.get(0));
    	} catch (Exception e) {
    		logger.error("failed scraping article: " + a.url);
    		e.printStackTrace();
    	}
		return null;
	}
}
