package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.processor.application.dto.ProcessingResult;

@Component
public class ProcessingResultProducer {
	@Autowired private KafkaTemplate<String, ProcessingResult> templateArticle;
	
	public void produce(ProcessingResult result) {
		templateArticle.send("processing-result", result);
	}
}
