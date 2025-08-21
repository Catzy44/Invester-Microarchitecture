package me.catzy.invester.master.service;

import org.springframework.stereotype.Service;

import me.catzy.invester.master.domain.marketEvent.MarketEvent;
import me.catzy.invester.master.generic.GenericServiceImpl;
import me.catzy.invester.master.repository.MarketEventRepository;

@Service
public class MarketEventService extends GenericServiceImpl<MarketEvent, Long>{
	public MarketEventService(MarketEventRepository repository) {
		super(repository);
	}
}
