package me.catzy.invester.master.application.factory;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.catzy.invester.kafka.messages.AIProcessingResultEnvelope;
import me.catzy.invester.master.domain.marketEvent.MarketEvent;

@Component
public class MarketEventFactory {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public MarketEvent[] parse(AIProcessingResultEnvelope result) throws JsonMappingException, JsonProcessingException {
		String rnt = result.getMessage();
		rnt = rnt.replace("```json", "");
		rnt = rnt.replace("```", "");
		
		return objectMapper.readValue(rnt, MarketEvent[].class);
	}
}
