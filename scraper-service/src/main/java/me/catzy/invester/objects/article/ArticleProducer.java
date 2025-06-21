package me.catzy.invester.objects.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleProducer {
	@Autowired private KafkaTemplate<String, Article> kafkaTemplate;
	
	public void produce(Article article) {
		kafkaTemplate.send("article-raw", article.getUrl(), article);
	}
}
