package me.catzy.invester.master.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.catzy.invester.master.domain.ArticleEntity;

@RepositoryRestResource(collectionResourceRel = "master_article", path = "master_article")
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
	public ArticleEntity findByUrl(String url);
	public List<ArticleEntity> findByEventsIsEmptyAndContentIsNotNullOrderByTimestampDesc();
	
	 @Query("SELECT a FROM Article a ORDER BY a.id DESC")
	 public List<ArticleEntity> getArticlesChunk(Pageable pageable);
}
