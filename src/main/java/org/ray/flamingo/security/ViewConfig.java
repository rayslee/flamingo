package org.ray.flamingo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry resgistry){
		resgistry.addViewController("/").setViewName("login");
		resgistry.addViewController("/login");
	}
	
}