package com.lagou.edu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
//        System.setProperty("spring.application.name","edu-gateway-boot");
//        System.setProperty("server.port","9001");
        SpringApplication.run(GatewayApplication.class,args);
    }
}
