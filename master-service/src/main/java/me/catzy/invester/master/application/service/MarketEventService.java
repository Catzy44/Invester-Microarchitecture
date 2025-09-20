package me.catzy.invester.master.application.service;

import org.springframework.stereotype.Service;

import me.catzy.invester.master.domain.MarketEventEntity;
import me.catzy.invester.master.generic.GenericServiceImpl;
import me.catzy.invester.master.repository.MarketEventRepository;

@Service
public class MarketEventService extends GenericServiceImpl<MarketEventEntity, Long> {
	public MarketEventService(MarketEventRepository repository) {
		super(repository);
	}
}
