package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AIMessage {
	public String role = null;
	public String content = null;
	public List<ToolCall> tool_calls = null;
}