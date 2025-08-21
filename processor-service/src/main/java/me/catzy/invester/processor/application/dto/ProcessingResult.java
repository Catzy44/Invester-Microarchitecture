package me.catzy.invester.processor.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIResponse;

@Getter
@Setter
@AllArgsConstructor
public class ProcessingResult {
	private AIResponse response;
}
