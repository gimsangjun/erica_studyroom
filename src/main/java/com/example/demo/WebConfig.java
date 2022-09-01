package com.example.demo;

import com.example.demo.interceptor.LogInterceptor;
import com.example.demo.interceptor.LogInCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 스프링이 제공하는 addInterceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Log 인터셉터
/*        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/**.css","/css/**","/*.ico","/error");*/

        // 로그인 인터셉터
        registry.addInterceptor(new LogInCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/","/login","/logout","/signUp",
                        "/css/**", "/*.ico", "/error"
                );
    }
}
