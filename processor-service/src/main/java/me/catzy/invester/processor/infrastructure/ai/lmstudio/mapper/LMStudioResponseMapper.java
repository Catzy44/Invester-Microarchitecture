package me.catzy.invester.processor.infrastructure.ai.lmstudio.mapper;

import org.springframework.stereotype.Component;

import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponse;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponseParsed;

@Component
public class LMStudioResponseMapper {
	/*public AIProcessingResultEnvelope build(LMStudioAPIResponse res) {
		String replyFull = res.choices.get(0).message.content;
		String[] replyFragmented = replyFull.split("</think>\n\n");
		
		String thinking = replyFragmented[0];
		String reply = replyFragmented[1];
        
		return new AIProcessingResultEnvelope(null, thinking, reply);
	}*/
	
	public LMStudioAPIResponseParsed build(LMStudioAPIResponse res) {
		String replyFull = res.choices.get(0).message.content;
		String[] replyFragmented = replyFull.split("</think>\n\n");
		
		String thinking = replyFragmented[0];
		String reply = replyFragmented[1];
        
		return new LMStudioAPIResponseParsed(thinking, reply);
	}
}
