package com.jm.authserver.config;

import com.jm.authserver.interceptor.AccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Createy Administrator on 2018/1/30.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/").excludePathPatterns("/login.jsp");
    }
}
