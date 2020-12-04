package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName UserApplication
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-27 11:03
 * @Version V1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserApplication {


    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);
    }
}
