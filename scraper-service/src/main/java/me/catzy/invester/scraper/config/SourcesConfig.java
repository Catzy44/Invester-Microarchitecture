package me.catzy.invester.scraper.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SourcesConfig {
	private List<String> rssFeeds = List.of(
		    "https://www.fxstreet.com/rss/news",
		    "https://www.fxstreet.com/rss/stocks",
		    "https://pl.investing.com/rss/news_14.rss", // gospodarcze
		    "https://pl.investing.com/rss/news_95.rss", // o wskaźnikach ekonomicznych
		    "https://pl.investing.com/rss/stock_Indices.rss",
		    "https://pl.investing.com/rss/commodities_Metals.rss",
		    "https://pl.investing.com/rss/market_overview_Fundamental.rss" // analiza fundamentalna
		);
	
	@Bean
	public List<String> getSources() {
		return rssFeeds;
	}
}
