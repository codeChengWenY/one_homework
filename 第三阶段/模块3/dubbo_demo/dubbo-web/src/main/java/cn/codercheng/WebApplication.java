package cn.codercheng;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName WebAplication
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-11 10:38
 * @Version V1.0
 **/
@SpringBootApplication
public class WebApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }

    @Configuration
    @EnableDubbo
    @PropertySource("classpath:/dubbo-consumer.properties")
    class ConsumerConfiguration {

    }


}
