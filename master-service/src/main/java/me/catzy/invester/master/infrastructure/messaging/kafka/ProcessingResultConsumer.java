package me.catzy.invester.master.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.catzy.invester.master.application.service.ProcessingResultService;
import me.catzy.invester.master.infrastructure.messaging.kafka.dto.AIProcessingResultMessage;

@Component
public class ProcessingResultConsumer {
	
	@Autowired private ProcessingResultService service;
	
	//@KafkaListener(topics = "article-raw", containerFactory = "articleRawFactory")
    //public void onMarketEvent(ConsumerRecord<String, Article> record) {
	@KafkaListener(topics = "processing-result")
	public void onMarketEvent(AIProcessingResultMessage job) {
		//service.process(job.getMessages());
		service.handle(job);
    }
}
