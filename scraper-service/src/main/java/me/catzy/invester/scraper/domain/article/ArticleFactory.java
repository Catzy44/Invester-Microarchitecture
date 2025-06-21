package me.catzy.invester.scraper.domain.article;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

@Service
public class ArticleFactory {
	public Article fromRssElement(Element item) {
        Article article = new Article();
        article.title = ArticleHelper.getItem(item,"title");
        article.url = ArticleHelper.getItem(item,"link");
        article.content = ArticleHelper.getItem(item,"description");
        
        String date = item.getElementsByTagName("pubDate").item(0).getTextContent();
        article.setTimestamp(new Timestamp(ArticleHelper.parseDate(date).getTime()));
		return article;
	}
}
