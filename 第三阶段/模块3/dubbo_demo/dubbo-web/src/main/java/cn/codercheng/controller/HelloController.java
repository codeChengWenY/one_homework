package cn.codercheng.controller;

import com.lagou.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserController
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-11 11:04
 * @Version V1.0
 **/
@Slf4j
@RestController
public class HelloController {


    // 自定义线程池
    private ExecutorService executorService = new ThreadPoolExecutor(100, 200, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(2000));


    // 获取随机数
    final Random r = new Random();


    @DubboReference(loadbalance = "roundrobin")
    private HelloService helloService;


    @GetMapping(value = "/sayhello")
    public String sayHello(String name) {
        return helloService.sayHello(name, 0);
    }


    @GetMapping(value = "/batch/sayhello")
    public String order(int num) {

        for (int i = 0; i <= num; i++) {
            executorService.execute(() -> {
                log.info(helloService.sayHello("hello", r.nextInt(1000)));
                log.info(helloService.sayHello1("hello1", r.nextInt(1000)));
                log.info(helloService.sayHello2("hello2", r.nextInt(1000)));
            });
        }
        return "sayhello成功";
    }

    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(r.nextInt(1000));
    }


}
