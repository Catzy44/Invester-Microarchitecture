package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.catzy.invester.processor.application.service.MarketEventService;
import me.catzy.invester.processor.domain.article.Article;

@Component
public class RawArticleConsumer {
	@Autowired MarketEventService service;
	
	//@KafkaListener(topics = "article-raw", containerFactory = "articleRawFactory")
    //public void onMarketEvent(ConsumerRecord<String, Article> record) {
	@KafkaListener(topics = "article-raw")
	public void onMarketEvent(Article article) {
		service.processArticle(article);
    }
}
