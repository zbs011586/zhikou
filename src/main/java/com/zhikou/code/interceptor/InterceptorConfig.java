package com.zhikou.code.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    //此处需要将tokenInterceptor注册为一个bean交给工厂管理 否则在tokenInterceptor中就无法使用@Autowired注入
    @Bean
    public TokenInterceptor authorizationInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor());
        super.addInterceptors(registry);
    }
}

