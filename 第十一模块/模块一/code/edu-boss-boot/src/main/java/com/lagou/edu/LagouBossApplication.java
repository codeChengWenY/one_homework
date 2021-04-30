package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author CoderCheng
 * @date 2021/04/29 17:12
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.lagou.edu")
public class LagouBossApplication {

    public static void main(String[] args){
        SpringApplication.run(LagouBossApplication.class,args);
    }
}
