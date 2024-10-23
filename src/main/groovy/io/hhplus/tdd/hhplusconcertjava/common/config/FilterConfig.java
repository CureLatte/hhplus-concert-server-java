package io.hhplus.tdd.hhplusconcertjava.common.config;

import io.hhplus.tdd.hhplusconcertjava.common.filter.LogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public LogFilter logFilter() {
        return  new LogFilter();

    }
}
