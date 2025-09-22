package me.catzy.invester.processor.infrastructure.messaging.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIMessageEnvelope;
import me.catzy.invester.kafka.messages.AIProcessingJobEnvelope;
import me.catzy.invester.processor.application.service.ProcessorService;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIMessage;

@Component
public class ProcessingJobConsumer {
	//private static final Logger logger = LoggerFactory.getLogger(ProcessingJobConsumer.class);
	@Autowired ProcessorService service;
	
	@KafkaListener(topics = "processing-job")
	public void onMarketEvent(AIProcessingJobEnvelope job) {
		List<AIMessageEnvelope> messages = job.getMessages();
		
		service.process(messages.stream().map(env -> new AIMessage(env.getRole(), env.getContent(), null)).toList(), job.getPersistentJobId());
    }
}
