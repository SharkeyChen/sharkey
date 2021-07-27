package com.example.sharkey.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private IpInterceptor ipInterceptor;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private RemindInterceptor remindInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(ipInterceptor);
        registry.addInterceptor(tokenInterceptor);
        registry.addInterceptor(remindInterceptor);
    }
}
