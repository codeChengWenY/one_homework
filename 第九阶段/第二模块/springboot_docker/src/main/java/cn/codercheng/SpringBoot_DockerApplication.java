package cn.codercheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.codercheng.mapper")
public class SpringBoot_DockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot_DockerApplication.class, args);
    }

}
