package com.laogu.edu.generator;

import com.lagou.edu.course.LagouCourseApplication;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LagouCourseApplication.class})
public class TestMQ {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void test(){
        rocketMQTemplate.convertAndSend("tp_springboot","springboot:hello lagou");
    }
}
