package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import java.util.ArrayList;
import java.util.List;

/*LM STUDIO API FOR THE SAKE OF JACKSON*/
public class LMStudioAPIResponse {
	public String id;
	public String object;
	public long created;
	public String model;
	public  List<Choice> choices = new ArrayList<Choice>();
	public Usage usage;
	public Stats stats;
	public ModelInfo model_info;
	public LLMRuntime runtime;
}