package me.catzy.invester.kafka.messages;

import lombok.Getter;

@Getter
public class AIMessage {
	public String role = null;
	public String content = null;
}