package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author CoderCheng
 * @date 2021/04/29 17:12
 */
@SpringBootApplication
@EnableConfigServer
public class LagouConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(LagouConfigServer.class,args);
    }
}
