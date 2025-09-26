package me.catzy.invester.master.application.service;

import org.springframework.stereotype.Service;

import lombok.Getter;
import me.catzy.invester.master.domain.MarketEventEntity;
import me.catzy.invester.master.generic.GenericServiceImpl;
import me.catzy.invester.master.repository.MarketEventRepository;

@Service
@Getter
public class MarketEventService extends GenericServiceImpl<MarketEventEntity, Long> {
	private int chid = 0;
	
	public MarketEventService(MarketEventRepository repository) {
		super(repository);
	}
	
	@Override
	public MarketEventEntity save(MarketEventEntity entity) {
		chid++;//IDC about racing threads - the int just have to change.
        return repository.save(entity);
    }
}
