package com.example.demoserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.example.demoserviceprovider", "com.example.democommon"})
public class DemoServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoServiceProviderApplication.class, args);
    }

}
