package me.catzy.invester.objects.article;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import me.catzy.invester.objects.marketEvent.MarketEventService;

public class ProcessedArticleConsumer {
	private static final Logger logger = LoggerFactory.getLogger(ProcessedArticleConsumer.class);
	@Autowired private ArticleRepository repo;
	
	@KafkaListener(topics = "market-events", containerFactory = "marketEventFactory")
    public void onMarketEvent(ConsumerRecord<Long, Article> record) {
		repo.save(record.value());
    }
}
