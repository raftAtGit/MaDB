package org.madb.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

/** Web MVC configuration. Enables CORS (Cross-Origin Resource Sharing) and publishes javadocs. */
@Configuration
public class WebConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurer() {
			/** Enable CORS */
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
                		.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
			
			/** Publish javadocs at /javadoc path */
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
			    registry
			      .addResourceHandler("/javadoc/**")
			      .addResourceLocations("classpath:/javadoc/")
			      .setCachePeriod(3600)
			      .resourceChain(true)
			      .addResolver(new EncodedResourceResolver());
			}
			
			/** serve /javadoc and /javadoc/ path from /javadoc/index.html */
			@Override
		    public void addViewControllers(ViewControllerRegistry registry) {
		        registry.addViewController("/javadoc").setViewName("forward:/javadoc/index.html");
		        registry.addViewController("/javadoc/").setViewName("forward:/javadoc/index.html");
		    }
		};
	}
}
