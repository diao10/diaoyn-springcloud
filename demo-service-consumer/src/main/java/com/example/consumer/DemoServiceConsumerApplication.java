package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author diaoyn
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.example.consumer", "com.example.common"})
@Slf4j
public class DemoServiceConsumerApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication springApplication = new SpringApplication(DemoServiceConsumerApplication.class);

        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        Environment env = configurableApplicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("                        ----------------------------------------------------------\n" +
                "                        Application is running! Access URLs:\n\t" +
                "                        Local:    http://localhost:" + port + "\n\t" +
                "                        External: http://" + ip + ":" + port + " \n\t" +
                "                        Doc:      http://" + ip + ":" + port + "/doc.html\n" +
                "                        ----------------------------------------------------------");
    }
}
