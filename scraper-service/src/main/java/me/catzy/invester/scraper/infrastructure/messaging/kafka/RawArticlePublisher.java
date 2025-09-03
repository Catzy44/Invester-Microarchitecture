package me.catzy.invester.scraper.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.RawArticleEnvelope;

@Component
public class RawArticlePublisher {
	@Autowired private KafkaTemplate<String, RawArticleEnvelope> kafkaTemplate;
	
	public void produce(RawArticleEnvelope article) {
		kafkaTemplate.send("article-raw", article.getUrl(), article);
	}
}
