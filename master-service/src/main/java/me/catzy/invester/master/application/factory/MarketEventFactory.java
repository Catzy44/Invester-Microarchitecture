package me.catzy.invester.master.application.factory;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.catzy.invester.kafka.messages.AIProcessingResultEnvelope;
import me.catzy.invester.master.domain.MarketEventEntity;

@Component
public class MarketEventFactory {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public MarketEventEntity[] parse(AIProcessingResultEnvelope result) throws JsonMappingException, JsonProcessingException {
		String rnt = result.getMessage();
		rnt = rnt.replace("```json", "");
		rnt = rnt.replace("```", "");
		
		return objectMapper.readValue(rnt, MarketEventEntity[].class);
	}
}
