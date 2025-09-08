package me.catzy.invester.master.application.service;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	@Autowired ProcessingJobService serviceJob;
	
	private Logger logger = LoggerFactory.getLogger(ProcessingResultService.class);
	
	public void handle(AIProcessingResultEnvelope message) {
		
		ArticleProcessingJob persistentJob = jobPersistentRepo.findById(message.getPersistentJobId()).get();
		try {
			MarketEvent[] marketEvents = factory.parse(message);//
			for(MarketEvent e : marketEvents) {
				e.setArticle(persistentJob.getArticle());
			}
			
			Stream.of(marketEvents).forEach(entity -> repo.save(entity));
			
		} catch (JsonProcessingException e) {
			logger.error("AIProcessingResult JSON parsing FAILED!");
			logger.error("Retrying!");
			serviceJob.processArticle(persistentJob.getArticle());
			//e.printStackTrace();
		}
	}
}
