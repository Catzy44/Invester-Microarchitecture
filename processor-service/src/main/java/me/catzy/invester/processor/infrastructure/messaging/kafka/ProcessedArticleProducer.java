package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.processor.domain.article.Article;

@Component
public class ProcessedArticleProducer {
	@Autowired private KafkaTemplate<String, Article> templateArticle;
	
	public void produce(Article article) {
		templateArticle.send("article-processed", article.getUrl(), article);
	}
}
