package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName EmailApplication
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-23 14:42
 * @Version V1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class EmailApplication {


    public static void main(String[] args) {

        SpringApplication.run(EmailApplication.class, args);
    }
}
