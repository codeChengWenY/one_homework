package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName EmailApplication
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-23 14:42
 * @Version V1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.lagou.edu.pojo")
@EnableFeignClients
public class CodeApplication {


    public static void main(String[] args) {

        SpringApplication.run(CodeApplication.class, args);
    }
}
