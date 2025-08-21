package me.catzy.invester.master.application.service;

import org.springframework.stereotype.Service;

import me.catzy.invester.master.domain.article.Article;
import me.catzy.invester.master.generic.GenericServiceImpl;
import me.catzy.invester.master.repository.ArticleRepository;

@Service
public class ArticleService extends GenericServiceImpl<Article, Long> {

	public ArticleService(ArticleRepository repository) {
		super(repository);
	}
}
