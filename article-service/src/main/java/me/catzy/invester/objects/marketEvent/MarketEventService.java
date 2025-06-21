package me.catzy.invester.objects.marketEvent;

import org.springframework.stereotype.Service;

import me.catzy.invester.generic.GenericServiceImpl;

@Service
public class MarketEventService extends GenericServiceImpl<MarketEvent, Long>{
	public MarketEventService(MarketEventRepository repository) {
		super(repository);
	}
}
