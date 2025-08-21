package me.catzy.invester.master.application.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.catzy.invester.master.domain.marketEvent.MarketEvent;
import me.catzy.invester.master.infrastructure.messaging.kafka.dto.AIProcessingResultMessage;

public class MarketEventFactory {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public MarketEvent[] parse(AIProcessingResultMessage result) throws JsonMappingException, JsonProcessingException {
		String rnt = result.getMessage();
		rnt = rnt.replace("```json", "");
		rnt = rnt.replace("```", "");
		
		return objectMapper.readValue(rnt, MarketEvent[].class);
	}
}
