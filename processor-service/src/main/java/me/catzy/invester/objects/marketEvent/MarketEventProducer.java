package me.catzy.invester.objects.marketEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MarketEventProducer {
	@Autowired private KafkaTemplate<Long, MarketEvent> templateEvent;
	
	public void produce(MarketEvent me) {
		templateEvent.send("market-events", me.getId(), me);
	}
}
