package me.catzy.invester.processor.application.dto;

import lombok.Getter;
import lombok.Setter;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AICompletion;

@Getter
@Setter
public class ProcessingJob {
	private AICompletion job;
}
