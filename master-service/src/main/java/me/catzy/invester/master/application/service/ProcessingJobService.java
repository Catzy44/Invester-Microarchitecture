package me.catzy.invester.master.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.catzy.invester.master.application.factory.ProcessingJobFactory;
import me.catzy.invester.master.domain.article.Article;
import me.catzy.invester.master.infrastructure.messaging.kafka.ProcessingJobProducer;
import me.catzy.invester.master.infrastructure.messaging.kafka.dto.AIProcessingJobMessage;

@Service
public class ProcessingJobService {
	@Autowired ProcessingJobFactory factory;
	@Autowired ProcessingJobProducer producer;
	
	public void handleRawArticle(Article a) {
		AIProcessingJobMessage job = factory.createFromArticle(a);
		producer.produce(job);
	}
}
