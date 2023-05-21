package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper(){ return new ModelMapper(); }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:3001", "https://friendly-dango-f48d5f.netlify.app")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("authorization", "content-type", "ngrok-skip-browser-warning")
                .exposedHeaders("authorization")
                .allowCredentials(true)
                .maxAge(3000);
    }

}
