package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name, int timeToWait) {

        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sayHello方法{}"+name;
    }

    @Override
    public String sayHello1(String name, int timeToWait) {

        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sayHello1方法{}"+name;
    }

    @Override
    public String sayHello2(String name, int timeToWait) {

        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sayHello2方法{}"+name;
    }
}
