package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author CoderCheng
 * @date 2021/04/29 17:12
 */
@SpringBootApplication
@EnableEurekaServer
public class LagouEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(LagouEurekaApplication.class,args);
    }
}
