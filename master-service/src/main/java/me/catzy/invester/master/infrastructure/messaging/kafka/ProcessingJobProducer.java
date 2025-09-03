package me.catzy.invester.master.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIProcessingJobMessage;

@Component
public class ProcessingJobProducer {
	@Autowired private KafkaTemplate<String, AIProcessingJobMessage> templateArticle;
	
	public void produce(AIProcessingJobMessage result) {
		templateArticle.send("processing-job", result);
	}
}
