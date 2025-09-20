package me.catzy.invester.master.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.catzy.invester.kafka.messages.AIProcessingJobEnvelope;
import me.catzy.invester.master.application.factory.ProcessingJobFactory;
import me.catzy.invester.master.domain.ArticleEntity;
import me.catzy.invester.master.domain.ArticleProcessingJobEntity;
import me.catzy.invester.master.infrastructure.messaging.kafka.ProcessingJobProducer;
import me.catzy.invester.master.repository.ArticleProcessingJobRepository;
import me.catzy.invester.master.repository.ArticleRepository;

@Service
public class ProcessingJobService {
	@Autowired ProcessingJobFactory factory;
	@Autowired ProcessingJobProducer producer;
	@Autowired ArticleProcessingJobRepository jobPersistentRepo;
	@Autowired ArticleRepository articleRepo;
	
	public void handleRawArticle(ArticleEntity a) {
		a = articleRepo.save(a);
		processArticle(a);
	}
	
	public void processArticle(ArticleEntity a) {
		ArticleProcessingJobEntity persistentJob = new ArticleProcessingJobEntity();
		persistentJob.setArticle(a);
		persistentJob = jobPersistentRepo.save(persistentJob);
		
		AIProcessingJobEnvelope job = factory.createFromArticle(a);
		job.setPersistentJobId(persistentJob.getId());
		
		producer.produce(job);
	}
}
