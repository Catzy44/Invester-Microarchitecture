package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIProcessingResultEnvelope;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponseParsed;

@Component
public class ProcessingResultProducer {
	@Autowired private KafkaTemplate<Long, AIProcessingResultEnvelope> templateArticle;
	
	public void produce(LMStudioAPIResponseParsed result, Long persistentId) {
		templateArticle.send("processing-result",persistentId, new AIProcessingResultEnvelope(persistentId, result.getThinking(), result.getReply()));
	}
}
