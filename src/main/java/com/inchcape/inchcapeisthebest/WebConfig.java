package com.inchcape.inchcapeisthebest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/src/main/resources/templates")
				.addResourceLocations("src/main/resources/templates")
				.setCachePeriod(3600)
				.resourceChain(true);
	}
}
