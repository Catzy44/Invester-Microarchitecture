package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.processor.application.dto.AIProcessingResult;

@Component
public class ProcessingResultProducer {
	@Autowired private KafkaTemplate<String, AIProcessingResult> templateArticle;
	
	public void produce(AIProcessingResult result) {
		templateArticle.send("processing-result", result);
	}
}
