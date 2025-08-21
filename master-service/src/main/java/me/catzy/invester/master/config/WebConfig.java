package me.catzy.invester.master.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.core.config.ProjectionDefinitionConfiguration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.projection.ProjectionDefinitions;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
	public static void setCorsRegistry(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins(new String[] { "*" })
		.allowedMethods(new String[] { "GET", "PUT", "DELETE", "POST", "PATCH", "OPTIONS" })
		.allowCredentials(false)
		.maxAge(3600L);
	}
	
	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {
		return new RepositoryRestConfigurer() {
			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
				WebConfig.setCorsRegistry(cors);
			}
		};
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {//
			public void addCorsMappings(CorsRegistry registry) {
				WebConfig.setCorsRegistry(registry);
			}
		};
	}
	
	/*@Bean
	public CacheManager cacheManager() {
		return (CacheManager) new ConcurrentMapCacheManager(new String[] { "test" });
	}*/

	@Bean
	public ProjectionFactory projectionFactory() {
		return (ProjectionFactory) new SpelAwareProxyProjectionFactory();
	}

	@Bean
	public ProjectionDefinitions projectionDefinitions() {
		return (ProjectionDefinitions) new ProjectionDefinitionConfiguration();
	}
}
