package me.catzy.invester.scraper.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.RawArticleMessage;

@Component
public class RawArticlePublisher {
	@Autowired private KafkaTemplate<String, RawArticleMessage> kafkaTemplate;
	
	public void produce(RawArticleMessage article) {
		kafkaTemplate.send("article-raw", article.getUrl(), article);
	}
}
