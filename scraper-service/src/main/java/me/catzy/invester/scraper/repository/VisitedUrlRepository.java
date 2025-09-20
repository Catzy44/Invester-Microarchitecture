package me.catzy.invester.scraper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.catzy.invester.scraper.domain.rawarticle.VisitedUrlEntity;

@RepositoryRestResource(collectionResourceRel = "scraper_visited_url", path = "scraper_visited_url")
public interface VisitedUrlRepository extends JpaRepository<VisitedUrlEntity, Long>{

}
