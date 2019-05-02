package com.vacant;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("index");
    	registry.addViewController("/index").setViewName("index");
//    	registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/error").setViewName("error");
    }

}
