package me.catzy.invester.master.infrastructure.messaging.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AIProcessingResultMessage {
	private String thinking;
	private String message;
}
