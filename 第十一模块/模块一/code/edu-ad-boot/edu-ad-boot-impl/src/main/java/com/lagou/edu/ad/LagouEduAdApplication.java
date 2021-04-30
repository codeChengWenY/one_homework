package com.lagou.edu.ad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author CoderCheng
 * @date 2021/04/29 17:12
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lagou.edu.ad.mapper")
public class LagouEduAdApplication {
    public static void main(String[] args) {
        SpringApplication.run(LagouEduAdApplication.class,args);
    }
}
