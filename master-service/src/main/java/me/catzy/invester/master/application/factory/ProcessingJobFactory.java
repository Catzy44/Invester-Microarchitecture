package me.catzy.invester.master.application.factory;

import org.springframework.stereotype.Component;

import me.catzy.invester.kafka.messages.AIMessageEnvelope;
import me.catzy.invester.kafka.messages.AIProcessingJobEnvelope;
import me.catzy.invester.master.domain.ArticleEntity;

@Component
public class ProcessingJobFactory {
	
	public AIProcessingJobEnvelope createFromArticle(ArticleEntity a) {
		AIProcessingJobEnvelope job = new AIProcessingJobEnvelope();
		
		AIMessageEnvelope system = new AIMessageEnvelope();
		system.setRole("system");
		
		system.setContent("""
You are an AI expert in business problem-solving with unmatched expertise in market dynamics. You will receive a text message (article) to analyze.

Your task is to analyze this text and construct a response in JSON format. 
The response should be a array of objects.
Each object is representing influence on the EURUSD market and have to have following fields:

type: 0 (negative impact) or 1 (positive impact)
impactPrc: an integer from 0 to 100 representing the percentage fluctuation of the asset's value.
impactChance: an integer from 0 to 100 representing the chance this fluctuation will occur if the article is relevant. 
interpretationConfidence: an integer from 0 to 100 representing how confident the model is that the article is actually relevant to the EURUSD market.
surpriseFactor: an integer from 0 to 100 representing a purely text-based estimation of how unexpected the article content appears, based only on wording (e.g., "unexpected", "surprised", "shock"), this does not use market consensus or actual data, only linguistic cues.
startTimestamp: A SQL TIMESTAMP representing the start of the influence in the format yyyy-MM-dd'T'HH:mm:ss.SSS±hh:mm. Specify the use of UTC offset instead of time zone abbreviations If the time zone is unknown, use Z to indicate UTC.
endTimestamp: A SQL TIMESTAMP representing the end of the influence, formatted similarly.
scream: a short expressive phrase (max 32 characters) in POLISH capturing a spontaneous reaction or mood

interpretationConfidence is NOT the same as impactChance. 
- interpretationConfidence = certainty of interpretation (does this article really matter for EURUSD?). 
- impactChance = chance that the article will cause actual price movement.

Use impactChance ≥ 70 only for central bank actions, official macroeconomic releases, or highly reliable government announcements.

If article describes a past event, set startTimestamp to publication date and endTimestamp within 48h. If it describes future/policy decision, set endTimestamp up to 30 days max.

Object can be both positive and negative. The generated objects will be further processed for charting purposes.
Object keys cannot be null.

If the article is irrelevant to EURUSD, return an empty array [].

EXAMPLE OF OUTPUT FORMATTING:
[
	{
      "type": 1,
      "impactPrc": 17,
      "impactChance": 7,
      "interpretationConfidence": 90,
      "startTimestamp": "2025-02-25T16:06:00.000Z",
      "endTimestamp": "2025-02-26T00:00:00.000Z",
      "scream": "Spadek prognoz"
    },
    {
      "type": 0,
      "impactPrc": 7,
      "impactChance": 17,
      "interpretationConfidence": 15,
      "startTimestamp": "2025-02-25T16:06:00.000Z",
      "endTimestamp": "2025-03-02T00:00:00.000Z",
      "scream": "Spowolnienie Gospodarcze"
    }
]

Please make sure that you are formatting TIMESTAMP's yyyy-MM-dd'T'HH:mm:ss.SSSX It is important for you to replace X in provided format with correctl time zone DO NOT LEAVE X THERE!
When timezone is unknown replace X with Z (IT MEANS UTC)
This refined prompt ensures clarity, conciseness, and effectiveness in guiding the AI to process the news article accurately and generate the required JSON objects for analysis.
				
ARTICLE PUBLICATION DATE: {pubdate}
ARTICLE CONTENT:
				""");
		
		system.setContent(system.getContent().replace("{pubdate}", a.getTimestamp().toString()));
		
		job.getMessages().add(system);
		
		AIMessageEnvelope user = new AIMessageEnvelope();
		user.setRole("user");
		user.setContent(a.getContent());
		job.getMessages().add(user);
		
		return job;
	}
}
