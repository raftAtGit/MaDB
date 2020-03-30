package org.madb.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Web MVC configuration. Enables CORS (Cross-Origin Resource Sharing) and publishes javadocs. */
@Configuration
public class WebConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
                		.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
			
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
			    registry
			      .addResourceHandler("/javadoc/**")
			      .addResourceLocations("classpath:/static/javadoc/");
			 }
		};
	}
}
