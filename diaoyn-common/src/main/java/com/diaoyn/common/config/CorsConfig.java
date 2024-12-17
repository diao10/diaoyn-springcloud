package com.diaoyn.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域解决
 *
 * @author diaoyn
 * @ClassName CorsConfig
 * @Date 2024/12/16 15:30
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有路径应用跨域配置
                .allowedOrigins("*") // 允许任何域进行跨域访问
                .allowedMethods("*") // 允许的请求方法
                .maxAge(3600) // 预检请求的缓存时间
                .allowedHeaders("*"); // 允许的请求头
    }
}
