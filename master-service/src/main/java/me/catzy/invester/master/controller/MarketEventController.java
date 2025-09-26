package me.catzy.invester.master.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catzy.invester.master.application.service.MarketEventService;
import me.catzy.invester.master.domain.MarketEventEntity;
import me.catzy.invester.master.generic.GenericController;
import me.catzy.invester.master.repository.MarketEventRepository;
import me.catzy.invester.master.repository.MarketEventRepository.EstimationDTO;

@RestController
@RequestMapping({ "/marketEvents"})
public class MarketEventController extends GenericController<MarketEventEntity, Long>{
	public MarketEventController(MarketEventService service) {
		this.service = service;
		super(service);
	}
	private MarketEventService service;
	@Autowired MarketEventRepository repo;
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	private static class EstimateRequestBody {
		Timestamp timestamp;
	}
	
	@PostMapping("/estimate")
	public EstimationDTO estimate(@RequestBody EstimateRequestBody body) throws Exception {
		return repo.getDailyEstimation(body.timestamp);
	}
	
	@JsonView(MarketEventEntity.values.class)
	@GetMapping("/current")
	public List<MarketEventEntity> currentEvents() {
		return repo.getCurrentEvents();
	}
	
	@GetMapping("/metrics")
	public int getTimeSpentOnProcessing() {
		////return service.getTimeSpentOnProcessingPrc();
		return 0;
	}
	
	@GetMapping("chid")
	public int getChId() {
		return service.getChid();
	}
}
