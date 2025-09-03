package me.catzy.invester.kafka.messages;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawArticleMessage {
	public String url;
	public String title;
	public String content;
	public Timestamp timestamp;
	public Timestamp processedTimestamp;
}
