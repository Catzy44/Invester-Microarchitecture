package me.catzy.invester.kafka.messages;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIProcessingJobMessage {
	private List<AIMessage> messages;
}
