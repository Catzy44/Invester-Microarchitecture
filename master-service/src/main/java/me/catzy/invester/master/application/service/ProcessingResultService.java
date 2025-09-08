package me.catzy.invester.master.application.service;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import me.catzy.invester.kafka.messages.AIProcessingResultEnvelope;
import me.catzy.invester.master.application.factory.MarketEventFactory;
import me.catzy.invester.master.domain.article.ArticleProcessingJob;
import me.catzy.invester.master.domain.marketEvent.MarketEvent;
import me.catzy.invester.master.repository.ArticleProcessingJobRepository;
import me.catzy.invester.master.repository.MarketEventRepository;

@Service
public class ProcessingResultService {
	@Autowired MarketEventFactory factory;
	@Autowired MarketEventRepository repo;
	@Autowired ArticleProcessingJobRepository jobPersistentRepo;
	
	public void handle(AIProcessingResultEnvelope message) {
		try {
			ArticleProcessingJob persistentJob = jobPersistentRepo.findById(message.getPersistentJobId()).get();
			
			MarketEvent[] marketEvents = factory.parse(message);//
			for(MarketEvent e : marketEvents) {
				e.setArticle(persistentJob.getArticle());
			}
			
			Stream.of(marketEvents).forEach(entity -> repo.save(entity));
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
