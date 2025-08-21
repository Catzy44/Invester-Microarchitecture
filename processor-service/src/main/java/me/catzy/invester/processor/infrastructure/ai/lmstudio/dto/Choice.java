package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

public class Choice {
	public int index;
	public Object logprobs;
	public String finish_reason;
	public AIMessage message;
}