package me.catzy.invester.master.application.factory;

import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIMessage;
import me.catzy.invester.kafka.messages.AIProcessingJobMessage;
import me.catzy.invester.master.domain.article.Article;

@Component
public class ProcessingJobFactory {
	
	public AIProcessingJobMessage createFromArticle(Article a) {
		AIProcessingJobMessage job = new AIProcessingJobMessage();
		
		AIMessage system = new AIMessage();
		system.role = "system";
		
		system.content = """
You are an AI expert in business problem-solving with unmatched expertise in market dynamics. You will receive a text message (article) to analyze.

Your task is to analyze this text and construct a response in JSON format. 
The response should be a array of objects.
Each object is representing influence on the EURUSD market and have to have following fields:

type: 0 (negative impact) or 1 (positive impact)
impactPrc: an integer from 0 to 100 representing the percentage fluctuation of the asset's value.
impactChance integer from 0 to 100 representing the chance this fluctuation will occur.
startTimestamp: A SQL TIMESTAMP representing the start of the influence in the format yyyy-MM-dd'T'HH:mm:ss.SSS±hh:mm. Specify the use of UTC offset instead of time zone abbreviations If the time zone is unknown, use Z to indicate UTC.
endTimestamp: A SQL TIMESTAMP representing the end of the influence, formatted similarly.
scream: a short expressive phrase (max 32 characters) in POLISH capturing a spontaneous reaction or mood

Object can be both positive and negative. The generated objects will be further processed for charting purposes.
Object keys cannot be null.

EXAMPLE OF OUTPUT FORMATTING:
[
	{
      "type": 1,
      "impactPrc": 17,
      "impactChance": 7,
      "startTimestamp": "2025-02-25T16:06:00.000Z",
      "endTimestamp": "2025-02-26T00:00:00.000Z",
      "scream": "Weaker US Treasuries"
    },
    {
      "type": 0,
      "impactPrc": 7,
      "impactChance": 17,
      "startTimestamp": "2025-02-25T16:06:00.000Z",
      "endTimestamp": "2025-03-02T00:00:00.000Z",
      "scream": "Tariff Threats Sour Mood"
    }
]

Please make sure that you are formatting TIMESTAMP's yyyy-MM-dd'T'HH:mm:ss.SSSX It is important for you to replace X in provided format with correctl time zone DO NOT LEAVE X THERE!
When timezone is unknown replace X with Z (IT MEANS UTC)
This refined prompt ensures clarity, conciseness, and effectiveness in guiding the AI to process the news article accurately and generate the required JSON objects for analysis.
				
ARTICLE PUBLICATION DATE: {pubdate}
ARTICLE CONTENT:
				""";
		
		system.content = system.content.replace("{pubdate}", a.getTimestamp().toString());
		
		job.getMessages().add(system);
		
		AIMessage user = new AIMessage();
		user.role = "user";
		user.content = a.getContent();
		job.getMessages().add(user);
		
		return job;
	}
}
