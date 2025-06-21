package me.catzy.invester.objects.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.catzy.invester.objects.marketEvent.MarketEventService;

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
