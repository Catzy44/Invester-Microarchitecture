package me.catzy.invester.master.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIProcessingResultEnvelope;
import me.catzy.invester.master.application.service.ProcessingResultService;

@Component
public class ProcessingResultConsumer {
	
	@Autowired private ProcessingResultService service;
	
	//@KafkaListener(topics = "article-raw", containerFactory = "articleRawFactory")
    //public void onMarketEvent(ConsumerRecord<String, Article> record) {
	@KafkaListener(topics = "processing-result")
	public void onMarketEvent(AIProcessingResultEnvelope result) {
		//service.process(job.getMessages());
		service.handle(result);
    }
}
