package com.lagou.edu.course;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lagou.edu.course.mapper")
public class LagouCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagouCourseApplication.class,args);
    }
}
