package me.catzy.invester.master.infrastructure.messaging.kafka.dto;

import lombok.Getter;

@Getter
public class AIMessage {
	public String role = null;
	public String content = null;
}