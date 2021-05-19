package cn.codercheng.kafka_demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaDemoApplicationTests {

    @Test
    void contextLoads() {
    }


    public static void main(String[] args) {


        System.out.println(Math.max(10, Math.min(1, 60)));


        System.out.println(Math.max(10, 50));

        boolean flag=false;

        Integer a=1;

        System.out.println(flag=flag ||null ==a);

    }

}
