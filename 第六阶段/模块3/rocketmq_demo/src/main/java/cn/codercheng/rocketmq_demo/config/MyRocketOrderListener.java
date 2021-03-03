package cn.codercheng.rocketmq_demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyRocketOrderListener
 * @Description:
 * @Author CoderCheng
 * @Date 2021-02-26 15:43
 * @Version V1.0
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "tp_order_pay_15", consumerGroup = "consumer_grp_12")
public class MyRocketOrderListener implements RocketMQListener<String> {



    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void onMessage(String orderNum) {
        log.info("RocketMQ收到消息" + orderNum);
        //模拟订单业务处理
        log.info("接受到下单请求:"+orderNum);
        log.info("下单业务处理");
        log.info("等待用户支付");
        rocketMQTemplate.syncSend("pay_check_16", MessageBuilder.withPayload(orderNum).build(), 2000, 4);
    }
}
