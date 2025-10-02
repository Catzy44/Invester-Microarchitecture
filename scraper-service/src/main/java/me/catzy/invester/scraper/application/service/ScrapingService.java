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

import jakarta.annotation.PostConstruct;
import me.catzy.invester.kafka.messages.RawArticleEnvelope;
import me.catzy.invester.scraper.application.factory.RawArticleFactory;
import me.catzy.invester.scraper.config.SourcesConfig;
import me.catzy.invester.scraper.domain.rawarticle.VisitedUrlEntity;
import me.catzy.invester.scraper.infrastructure.messaging.kafka.RawArticlePublisher;
import me.catzy.invester.scraper.infrastructure.scraping.parser.ArticleParser;
import me.catzy.invester.scraper.infrastructure.scraping.selenium.WebDriverService;
import me.catzy.invester.scraper.repository.VisitedUrlRepository;
import me.catzy.invester.scraper.util.Utils;

@Service
public class ScrapingService {
	private static final Logger logger = LoggerFactory.getLogger(ScrapingService.class);
	
	@Autowired private WebDriverService serviceWeb;
	@Autowired private RawArticlePublisher articleProd;
	@Autowired private RawArticleFactory articleFactory;
	@Autowired private Cache<String, Boolean> urlCache;
	@Autowired private SourcesConfig sourcesConfig;
	@Autowired private VisitedUrlRepository repo;
	
	@PostConstruct
	public void init() {
		List<VisitedUrlEntity> processedArticles = repo.findAll();
		processedArticles.stream().forEach(a -> urlCache.put(a.getUrl(), true));
	}
	
	//TODO implement better cache&database synchronization for multi-instance scenarios
	public void addToCache(String url) {
		urlCache.put(url, true);
		repo.save(new VisitedUrlEntity(url));
	}
	
	
	@Scheduled(fixedRate = 2, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void checkForAnyNews() throws MalformedURLException, Exception {
		logger.info("checking for news...");

		for(String s : sourcesConfig.getSources()) {
			try {
				processRSS(new URI(s).toURL());
			} catch (Exception e) {
				logger.error("failed processing one of provided RSS's");
				e.printStackTrace();
			}
		}
		serviceWeb.close();
	}
	
	public void processRSS(URL url) throws Exception {
		NodeList items = ArticleParser.loadDoc(url);
        
        List<RawArticleEnvelope> articles = new ArrayList<RawArticleEnvelope>();

        for (int i = 0; i < items.getLength(); i++) {
        	if(!(items.item(i) instanceof Element item)) {
        		continue;
        	}
            articles.add(articleFactory.fromRssElement(item));
        }
        
        for(RawArticleEnvelope article : articles) {
        	if(urlCache.getIfPresent(article.getUrl()) != null) {
        		continue;
        	}
        	addToCache(article.getUrl());
        	logger.info("New article: {}", article.getUrl());
        	
        	article.setContent(scrapeArticleContent(article));
        	
        	if(article.getContent() == null || article.getContent().isEmpty()) {
                logger.error("Failed to scrape article: {}", article.getUrl());
        		continue;
        	}
        	
        	articleProd.produce(article);
        }
	}
	
	private String scrapeArticleContent(RawArticleEnvelope a) {
		try {
			WebDriver driver = serviceWeb.get();

			driver.get(a.getUrl());

			// 1
			List<WebElement> el = driver.findElements(By.id("article"));

			// 2
			if (el.isEmpty()) {
				//logger.warn("article web-element not found - trying alternative method, URL:" + a.getUrl());
				el = driver.findElements(By.className("articlePage"));
			}

			// 3
			if (el.isEmpty()) {
                //logger.warn("article web-element not found AGAIN - trying alternative method (FX), URL:{}", a.getUrl());
				el = driver.findElements(By.className("fxs_article_content"));
			}
			
			if (el.isEmpty()) {
                logger.warn("article web-element not found AGAIN - trying alternative method (FX), URL:{}", a.getUrl());
                throw new Exception("Failed to scrape article!");
			}
			
			return Utils.extractArticleText(el.get(0));
    	} catch (Exception e) {
            logger.error("failed scraping article: {}", a.getUrl());
    		e.printStackTrace();
    	}
		return null;
	}
}
