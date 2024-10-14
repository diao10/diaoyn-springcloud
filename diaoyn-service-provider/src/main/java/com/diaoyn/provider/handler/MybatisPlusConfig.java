package com.diaoyn.provider.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author diaoyn
 * @ClassName MybatisPlusConfig
 * @Date 2024/10/14 11:04
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public BatchSqlInjector sqlInjector() {
        return new BatchSqlInjector();
    }
}
