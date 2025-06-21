package me.catzy.invester.objects.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProcessedArticleProducer {
	@Autowired private KafkaTemplate<String, Article> templateArticle;
	
	public void produce(Article article) {
		templateArticle.send("article-processed", article.getUrl(), article);
	}
}
