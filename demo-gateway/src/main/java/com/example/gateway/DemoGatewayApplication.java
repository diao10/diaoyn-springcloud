package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author diaoyn
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.example.gateway", "com.example.common"})
public class DemoGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGatewayApplication.class, args);
    }

}
