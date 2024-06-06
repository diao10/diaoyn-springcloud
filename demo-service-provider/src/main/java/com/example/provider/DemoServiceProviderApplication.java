package com.example.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diaoyn
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.example.provider", "com.example.common"})
public class DemoServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoServiceProviderApplication.class, args);
    }

}
