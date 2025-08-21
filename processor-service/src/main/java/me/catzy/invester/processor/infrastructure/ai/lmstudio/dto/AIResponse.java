package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import lombok.AllArgsConstructor;
import me.catzy.invester.processor.domain.marketEvent.MarketEvent;

@AllArgsConstructor
public class AIResponse {
	public MarketEvent[] events;
	public String response;
}