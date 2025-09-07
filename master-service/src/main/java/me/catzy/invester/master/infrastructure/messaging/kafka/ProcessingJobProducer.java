package me.catzy.invester.master.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIProcessingJobEnvelope;

@Component
public class ProcessingJobProducer {
	@Autowired private KafkaTemplate<String, AIProcessingJobEnvelope> templateArticle;
	
	public void produce(AIProcessingJobEnvelope result) {
		templateArticle.send("processing-job", result);//
	}
}
