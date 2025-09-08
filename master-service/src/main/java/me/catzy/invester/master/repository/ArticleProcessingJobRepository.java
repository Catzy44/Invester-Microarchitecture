package me.catzy.invester.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.catzy.invester.master.domain.article.ArticleProcessingJob;

@RepositoryRestResource(collectionResourceRel = "article_processing_job", path = "article_processing_job")
public interface ArticleProcessingJobRepository extends JpaRepository<ArticleProcessingJob, Long>{

}
