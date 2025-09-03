package me.catzy.invester.kafka.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AIProcessingResultEnvelope {
	private String thinking;
	private String message;
}
