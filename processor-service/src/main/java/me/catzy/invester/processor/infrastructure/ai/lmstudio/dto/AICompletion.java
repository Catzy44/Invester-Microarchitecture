package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/*IM LEAVING SETTINGS THERE - ITS A STATIC CLASS SO ITS NOT WRONG*/
@Getter
public class AICompletion {
	public String model = "deepseek-r1-distill-qwen-14b";
	public float temperature = 0.7f;
	public boolean stream = false;
	public int max_tokens = -1;
	public  List<AIMessage> messages = new ArrayList<AIMessage>();
}