package com.ejet.core.cloud.config;


import com.ejet.core.cloud.feign.CoFeignRequestHeaderInterceptor;
import com.ejet.core.cloud.feign.FeignHystrixConcurrencyStrategy;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Copyright (C), 2016-2018
 * @FileName: CoFeignConfiguration
 * @Author:   ShenYijie(Ejet)
 * @CreateDate:     2019/4/13 16:42
 * @Description:    WEB配置
 * @History:
 * @Version: 1.0
 */
@Slf4j
@Configuration
@EnableCaching
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CoFeignConfiguration implements WebMvcConfigurer {


    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor requestInterceptor() {
        return new CoFeignRequestHeaderInterceptor();
    }

    @Bean
    public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new FeignHystrixConcurrencyStrategy();
    }

}
