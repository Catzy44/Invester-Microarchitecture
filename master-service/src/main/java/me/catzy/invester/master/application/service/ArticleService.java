package me.catzy.invester.master.application.service;

import org.springframework.stereotype.Service;

import me.catzy.invester.master.domain.ArticleEntity;
import me.catzy.invester.master.generic.GenericServiceImpl;
import me.catzy.invester.master.repository.ArticleRepository;

@Service
public class ArticleService extends GenericServiceImpl<ArticleEntity, Long> {
	public ArticleService(ArticleRepository repository) {
		super(repository);
	}
}
