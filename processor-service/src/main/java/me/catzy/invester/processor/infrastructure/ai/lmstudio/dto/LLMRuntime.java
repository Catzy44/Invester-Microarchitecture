package me.catzy.invester.processor.infrastructure.ai.lmstudio.dto;

import java.util.ArrayList;
import java.util.List;

public class LLMRuntime {
	public String name;
	public String version;
	public List<String> supported_formats = new ArrayList<String>();
}