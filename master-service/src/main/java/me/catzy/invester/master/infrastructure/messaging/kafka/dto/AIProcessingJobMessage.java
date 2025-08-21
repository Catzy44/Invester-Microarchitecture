package me.catzy.invester.master.infrastructure.messaging.kafka.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIProcessingJobMessage {
	private List<AIMessage> messages;
}
