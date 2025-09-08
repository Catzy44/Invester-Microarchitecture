package me.catzy.invester.master.domain.article;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catzy.invester.master.domain.marketEvent.MarketEvent;

@Getter
@Setter
@Entity
@Table(name = "article")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonView({Article.values.class})
@NoArgsConstructor
public class Article {
	public Article(String url, String title, String content, Timestamp timestamp) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	public interface values  extends id{}
	
	public static interface id {}
	@JsonView({id.class})
	@Id
	@Access(AccessType.PROPERTY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public String url;
	public String title;
	
	public static interface content {}
	@JsonView({content.class})
	public String content;
	
	@OrderBy("sort ASC")
	@Column(name="published_timestamp")
	public Timestamp timestamp;
	
	@Column(name="processed_timestamp")
	public Timestamp processedTimestamp;
	
	public interface vMarketEvents {}
	@JsonView(vMarketEvents.class)
	@OneToMany(mappedBy="article")
	public List<MarketEvent> events;
}
