package me.catzy.invester.scraper.domain.rawarticle;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scraper_visited_url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitedUrlEntity {
	public VisitedUrlEntity(String url) {
		this.url = url;
	}
	@Id
	@Access(AccessType.PROPERTY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String url;
}
