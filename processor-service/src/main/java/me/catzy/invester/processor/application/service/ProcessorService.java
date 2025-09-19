package me.catzy.invester.processor.application.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.catzy.invester.processor.infrastructure.ai.lmstudio.LMStudioClient;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIMessage;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponseParsed;
import me.catzy.invester.processor.infrastructure.messaging.kafka.ProcessingResultProducer;

@Service
public class ProcessorService {
	private static final int AI_PROCESSING_RETRIES = 3;
	private static final Logger logger = LoggerFactory.getLogger(ProcessorService.class);
	@Autowired private LMStudioClient serviceLM;
	@Autowired private ProcessingResultProducer resultProducer;
	
	public void process(List<AIMessage> messages, Long jobPersistenceId) {
		logger.info("AI processing started");
		
		for (int i = 0; i < AI_PROCESSING_RETRIES; i++) {
			try {
				LMStudioAPIResponseParsed response = serviceLM.askAI(messages);
				
				resultProducer.produce(response, jobPersistenceId);
				
				logger.info("AI processing finished");
				return;
			} catch (Exception e) {
				logger.error("AI processing FAILED, attemp "+ (i+1) +" out of " +AI_PROCESSING_RETRIES);
				e.printStackTrace();
			}
		}
		logger.error("AI processing FAILED, abanding task");
		//TODO handle errors - message thru Kafka
	}
}
