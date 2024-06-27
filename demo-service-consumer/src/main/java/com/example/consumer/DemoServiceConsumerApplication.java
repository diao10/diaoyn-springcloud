package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author diaoyn
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.example.consumer", "com.example.common"})
@Slf4j
public class DemoServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoServiceConsumerApplication.class);

        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        Environment env = configurableApplicationContext.getEnvironment();
        log.info("                        ----------------------------------------------------------\n" +
                        "                        Application is running! Access URLs:\n" +
                        "                        Local:    http://localhost:{}\n" +
                        "                        Doc:      http://localhost:{}/doc.html\n" +
                        "                        ----------------------------------------------------------",
                env.getProperty("server.port"),
                env.getProperty("server.port"));
    }

}
