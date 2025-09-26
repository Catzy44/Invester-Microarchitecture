package me.catzy.invester.master.application.service;

import org.springframework.stereotype.Service;

import lombok.Getter;
import me.catzy.invester.master.domain.ArticleEntity;
import me.catzy.invester.master.generic.GenericServiceImpl;
import me.catzy.invester.master.repository.ArticleRepository;

@Service
@Getter
public class ArticleService extends GenericServiceImpl<ArticleEntity, Long> {
	private long chid = 0;
	
	public ArticleService(ArticleRepository repository) {
		super(repository);
	}
	
	@Override
    public ArticleEntity save(ArticleEntity entity) {
		chid++;//IDC about racing threads - the int just have to change.
        return repository.save(entity);
    }
}
