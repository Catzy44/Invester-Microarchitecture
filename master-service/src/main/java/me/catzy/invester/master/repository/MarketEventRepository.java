package me.catzy.invester.master.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.catzy.invester.master.domain.MarketEventEntity;

@RepositoryRestResource(collectionResourceRel = "master_market_events", path = "master_market_events")
public interface MarketEventRepository extends JpaRepository<MarketEventEntity, Long> {
	
	@AllArgsConstructor
	@Getter
	@Setter
	public static class EstimationDTO {
		Long positive;
		Long negative;
	}
	@Query("SELECT NEW me.catzy.invester.master.repository.MarketEventRepository$EstimationDTO( " +
		       "SUM(CASE WHEN me.type = 1 THEN me.impactPrc ELSE 0 END), " +
		       "SUM(CASE WHEN me.type = 0 THEN me.impactPrc ELSE 0 END)) " +
		       "FROM MarketEventEntity me " +
		       "WHERE DATE(me.startTimestamp) = DATE(:day) ")
	public EstimationDTO getDailyEstimation(@Param("day") Timestamp day);
	
	@Query("SELECT m FROM MarketEventEntity m WHERE m.endTimestamp > CURRENT_TIMESTAMP")
	public List<MarketEventEntity> getCurrentEvents();
	
}
