package com.zhikou.code.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticConfig implements WebMvcConfigurer {

    //将TokenInterceptor交由工厂管理
    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    //配置静态资源拦截器放行
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor()).excludePathPatterns("/image/**").addPathPatterns("/**");
    }

    //配置静态资源的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/root/zhiko/zhikou/image/");
    }
}
