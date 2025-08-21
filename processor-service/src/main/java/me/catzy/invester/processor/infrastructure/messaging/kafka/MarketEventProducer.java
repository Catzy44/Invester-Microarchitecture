package me.catzy.invester.processor.infrastructure.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import me.catzy.invester.processor.domain.marketEvent.MarketEvent;

@Component
public class MarketEventProducer {
	@Autowired private KafkaTemplate<Long, MarketEvent> templateEvent;
	
	public void produce(MarketEvent me) {
		templateEvent.send("market-events", me.getId(), me);
	}
}
