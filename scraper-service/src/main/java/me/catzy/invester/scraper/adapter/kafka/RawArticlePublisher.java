package me.catzy.invester.scraper.adapter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.scraper.domain.article.Article;

@Component
public class RawArticlePublisher {
	@Autowired private KafkaTemplate<String, Article> kafkaTemplate;
	
	public void produce(Article article) {
		kafkaTemplate.send("article-raw", article.getUrl(), article);
	}
}
