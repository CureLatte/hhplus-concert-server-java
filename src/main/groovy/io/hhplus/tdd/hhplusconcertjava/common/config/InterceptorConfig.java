package io.hhplus.tdd.hhplusconcertjava.common.config;

import io.hhplus.tdd.hhplusconcertjava.common.intercepter.UserInterceptor;
import io.hhplus.tdd.hhplusconcertjava.common.intercepter.WaitQueueInterceptor;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.UserService;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    WaitService waitService;

    @Autowired
    UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WaitQueueInterceptor(waitService))
                .addPathPatterns("/concert/time/*", "/concert/seat/*", "/concert/reservation", "/pay/reservation");

        registry.addInterceptor(new UserInterceptor(userService))
                .addPathPatterns("/concert/time/*", "/concert/seat/*", "/concert/reservation", "/pay/reservation");
    }




}