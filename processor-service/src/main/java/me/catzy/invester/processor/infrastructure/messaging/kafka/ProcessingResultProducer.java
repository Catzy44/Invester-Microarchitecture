package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIProcessingResultEnvelope;

@Component
public class ProcessingResultProducer {
	@Autowired private KafkaTemplate<String, AIProcessingResultEnvelope> templateArticle;
	
	public void produce(AIProcessingResultEnvelope result) {
		templateArticle.send("processing-result", result);
	}
}
