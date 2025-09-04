package me.catzy.invester.master.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.RawArticleEnvelope;
import me.catzy.invester.master.application.service.ProcessingJobService;
import me.catzy.invester.master.domain.article.Article;

@Component
public class RawArticleConsumer {
	@Autowired ProcessingJobService service;
	
	//@KafkaListener(topics = "article-raw", containerFactory = "articleRawFactory")
    //public void onMarketEvent(ConsumerRecord<String, Article> record) {
	@KafkaListener(topics = "article-raw")
	public void onMarketEvent(RawArticleEnvelope a) {
		service.handleRawArticle(new Article(a.getUrl(),a.getTitle(),a.getContent(),a.getTimestamp()));
    }
}
