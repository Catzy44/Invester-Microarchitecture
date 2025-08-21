package me.catzy.invester.processor.infrastructure.messaging.kafka.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIMessage;

@Getter
@Setter
public class AIProcessingJobMessage {
	private List<AIMessage> messages;
}
