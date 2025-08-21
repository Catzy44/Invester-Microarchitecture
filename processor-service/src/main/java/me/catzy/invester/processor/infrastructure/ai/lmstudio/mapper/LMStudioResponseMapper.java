package me.catzy.invester.processor.infrastructure.ai.lmstudio.mapper;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.catzy.invester.processor.domain.marketEvent.MarketEvent;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIResponse;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponse;

@Component
public class LMStudioResponseMapper {
	private static ObjectMapper objectMapper = new ObjectMapper();

	public AIResponse tryToParseResponse(LMStudioAPIResponse r) throws JsonMappingException, JsonProcessingException {
		String rnt = getReplyNoThinking(r);
		
		rnt = rnt.replace("```json", "");
		rnt = rnt.replace("```", "");
		
		MarketEvent[] events = objectMapper.readValue(rnt, MarketEvent[].class);
        
        return new AIResponse(events,rnt);
	}
	
	public String getReplyNoThinking(LMStudioAPIResponse r) {
		String reply = r.choices.get(0).message.content;
        return reply.split("</think>\n\n")[1];
	}
}
