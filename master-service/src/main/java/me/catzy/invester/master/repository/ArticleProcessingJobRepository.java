package me.catzy.invester.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.catzy.invester.master.domain.ArticleProcessingJobEntity;

@RepositoryRestResource(collectionResourceRel = "master_article_processing_job", path = "master_article_processing_job")
public interface ArticleProcessingJobRepository extends JpaRepository<ArticleProcessingJobEntity, Long>{

}
