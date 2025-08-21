package me.catzy.invester.master.application.service;

import org.springframework.stereotype.Service;

import me.catzy.invester.master.infrastructure.messaging.kafka.dto.AIProcessingResultMessage;

@Service
public class ProcessingResultService {
	public void handle(AIProcessingResultMessage message) {
		
	}
}
