package me.catzy.invester.processor.infrastructure.ai.lmstudio.mapper;

import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIProcessingResultEnvelope;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponse;

@Component
public class LMStudioResponseMapper {
	public AIProcessingResultEnvelope build(LMStudioAPIResponse res) {
		String replyFull = res.choices.get(0).message.content;
		String[] replyFragmented = replyFull.split("</think>\n\n");
		
		String thinking = replyFragmented[0];
		String reply = replyFragmented[1];
        
		return new AIProcessingResultEnvelope(thinking, reply);
	}
}
