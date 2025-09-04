package me.catzy.invester.kafka.messages;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AIProcessingJobEnvelope {
	private List<AIMessageEnvelope> messages;
}
