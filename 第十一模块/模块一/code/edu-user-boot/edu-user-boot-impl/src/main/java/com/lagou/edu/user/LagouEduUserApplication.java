package com.lagou.edu.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiongfei
 * @location shenzhen.china
 * @date 2020/11/12 17:44
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lagou.edu.user.mapper")
public class LagouEduUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LagouEduUserApplication.class,args);
    }
}
