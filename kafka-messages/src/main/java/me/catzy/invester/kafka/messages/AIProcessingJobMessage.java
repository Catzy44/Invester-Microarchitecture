package me.catzy.invester.kafka.messages;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIMessage;

@Getter
@Setter
public class AIProcessingJobMessage {
	private List<AIMessage> messages;
}
