package com.petlife.platform.framework.config;

import com.petlife.platform.framework.interceptor.UserTypeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserTypeInterceptor userTypeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userTypeInterceptor)
                .addPathPatterns("/app/**")   // 只拦app接口
                .excludePathPatterns( //登录注册相关接口不拦截
                        "/app/auth/login",
                        "/app/auth/sendCode",
                        "/app/auth/logout",
                        "/publicKey",
                        "/dev-api/captchaImage",
                        "/dev-api/login"
                );
    }
}