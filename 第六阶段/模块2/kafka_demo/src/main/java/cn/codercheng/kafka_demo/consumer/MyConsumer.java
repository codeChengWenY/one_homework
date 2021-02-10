package cn.codercheng.kafka_demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyConsumer
 * @Description:
 * @Author CoderCheng
 * @Date 2021-02-09 16:11
 * @Version V1.0
 **/
@Component
@Slf4j
public class MyConsumer {



    @KafkaListener(topics ="topic-log")
    public void onMessage(ConsumerRecord<Integer, String> record) {

        log.info("消费者收到的消息" + record.value());

    }
}
