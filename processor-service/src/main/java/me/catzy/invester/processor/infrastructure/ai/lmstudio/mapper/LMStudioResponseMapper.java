package me.catzy.invester.processor.infrastructure.ai.lmstudio.mapper;

import org.springframework.stereotype.Component;

import me.catzy.invester.processor.application.dto.AIProcessingResult;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponse;

@Component
public class LMStudioResponseMapper {
	public AIProcessingResult build(LMStudioAPIResponse res) {
		String replyFull = res.choices.get(0).message.content;
		String[] replyFragmented = replyFull.split("</think>\n\n");
		
		String thinking = replyFragmented[0];
		String reply = replyFragmented[1];
        
		return new AIProcessingResult(thinking, reply);
	}
}
