package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import lombok.AllArgsConstructor;
import me.catzy.invester.processor.domain.MarketEventEntity;

@AllArgsConstructor
public class AIResponse {
	public MarketEventEntity[] events;
	public String response;
}