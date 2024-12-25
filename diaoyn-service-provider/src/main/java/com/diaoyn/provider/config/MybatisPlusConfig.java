package com.diaoyn.provider.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.diaoyn.provider.handler.BatchSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * MybatisPlus配置
 *
 * @author EDY
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); //分页插件
//        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor()); //乐观锁插件
        return interceptor;
    }

    @Bean
    public BatchSqlInjector sqlInjector() {
        return new BatchSqlInjector();
    }

}
