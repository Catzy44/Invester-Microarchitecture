package me.catzy.invester.kafka.messages;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RawArticleEnvelope {
	private String url;
	private String title;
	private String content;
	private Timestamp timestamp;
	private Timestamp processedTimestamp;
}
