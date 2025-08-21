package me.catzy.invester.scraper.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.scraper.domain.rawarticle.RawArticle;

@Component
public class RawArticlePublisher {
	@Autowired private KafkaTemplate<String, RawArticle> kafkaTemplate;
	
	public void produce(RawArticle article) {
		kafkaTemplate.send("article-raw", article.getUrl(), article);
	}
}
