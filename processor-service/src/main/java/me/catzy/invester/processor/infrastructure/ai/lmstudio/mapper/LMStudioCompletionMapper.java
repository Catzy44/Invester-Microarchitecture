package me.catzy.invester.processor.infrastructure.ai.lmstudio.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AICompletion;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIMessage;

@Component
public class LMStudioCompletionMapper {
	public AICompletion build(List<AIMessage> messages) {
		AICompletion c = new AICompletion();
		c.messages.addAll(messages);
		return c;
	}
}
