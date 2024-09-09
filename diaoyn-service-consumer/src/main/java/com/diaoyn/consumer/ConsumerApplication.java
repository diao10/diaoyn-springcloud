package com.diaoyn.consumer;

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
@ComponentScan(basePackages = {"com.diaoyn.consumer", "com.diaoyn.common"})
@Slf4j
public class ConsumerApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication springApplication = new SpringApplication(ConsumerApplication.class);

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
