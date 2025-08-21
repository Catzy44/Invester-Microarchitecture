package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.catzy.invester.processor.application.service.ProcessorService;
import me.catzy.invester.processor.infrastructure.messaging.kafka.dto.AIProcessingJobMessage;

@Component
public class ProcessingJobConsumer {
@Autowired ProcessorService service;
	
	//@KafkaListener(topics = "article-raw", containerFactory = "articleRawFactory")
    //public void onMarketEvent(ConsumerRecord<String, Article> record) {
	@KafkaListener(topics = "processing-job")
	public void onMarketEvent(AIProcessingJobMessage job) {
		service.process(job.getMessages());
    }
}
