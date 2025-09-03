package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIMessage {
	public String role = null;
	public String content = null;
}