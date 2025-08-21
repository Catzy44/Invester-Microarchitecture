package me.catzy.invester.processor.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.catzy.invester.processor.application.dto.ProcessingJob;
import me.catzy.invester.processor.application.dto.ProcessingResult;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.LMStudioClient;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AICompletion;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIResponse;
import me.catzy.invester.processor.infrastructure.messaging.kafka.ProcessingResultProducer;

@Service
public class ProcessorService {
	private static final int AI_PROCESSING_RETRIES = 3;
	private static final Logger logger = LoggerFactory.getLogger(ProcessorService.class);
	@Autowired private LMStudioClient serviceLM;
	@Autowired private ProcessingResultProducer resultProducer;
	
	public void process(ProcessingJob job) {
		AICompletion completion = job.getJob();
		logger.debug("AI processing started");
		
		for (int i = 0; i < AI_PROCESSING_RETRIES; i++) {
			try {
				AIResponse response = serviceLM.askAI(completion);
				ProcessingResult result = new ProcessingResult(response);
				
				resultProducer.produce(result);
				logger.debug("AI processing finished");
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
