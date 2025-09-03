package me.catzy.invester.scraper.application.factory;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import me.catzy.invester.scraper.domain.rawarticle.RawArticle;
import me.catzy.invester.scraper.infrastructure.scraping.parser.ArticleParser;

@Service
public class RawArticleFactory {
	public RawArticle fromRssElement(Element item) {
        RawArticle article = new RawArticle();
        article.title = ArticleParser.getItem(item,"title");
        article.url = ArticleParser.getItem(item,"link");
        article.content = ArticleParser.getItem(item,"description");
        
        String date = item.getElementsByTagName("pubDate").item(0).getTextContent();
        article.setTimestamp(new Timestamp(ArticleParser.parseDate(date).getTime()));
		return article;
	}
}
