package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LMStudioAPIResponseParsed {
	String thinking;
	String reply;
}
