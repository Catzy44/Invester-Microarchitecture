package me.catzy.invester.master.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import me.catzy.invester.master.application.service.ArticleService;
import me.catzy.invester.master.domain.ArticleEntity;
import me.catzy.invester.master.domain.MarketEventEntity;
import me.catzy.invester.master.generic.GenericController;
import me.catzy.invester.master.repository.ArticleRepository;

@RestController
@RequestMapping({ "/articles"})
public class ArticleController extends GenericController<ArticleEntity, Long> {
	@Autowired ArticleService service;
	@Autowired ArticleRepository repo;
	
	public ArticleController(ArticleService service) {
        super(service);
    }
	
	private static interface getEvents extends ArticleEntity.vMarketEvents, MarketEventEntity.values {}
	@JsonView(getEvents.class)
	@GetMapping("/{id}/marketEvents")
	public List<MarketEventEntity> getEvents(@PathVariable long id) {
		return repo.findById(id).get().getEvents();//
	}

	@Data
	static class ArticleChunkQ {
		private int index;
		private int count;
	}
	private static interface getChunk extends ArticleEntity.values, ArticleEntity.vMarketEvents, MarketEventEntity.values {}
	@JsonView(getChunk.class)
	@PostMapping({ "/chunk" })
	public List<ArticleEntity> getArticlesChunk(@RequestBody ArticleChunkQ data) {
		Pageable pageable = PageRequest.of(data.getIndex(), 25 * data.getCount(), Sort.by("id").descending());
		return repo.getArticlesChunk(pageable);
	}
}
