package cn.codercheng.rocketmq_demo.config;

import cn.codercheng.rocketmq_demo.service.RedisHolder;
import cn.codercheng.rocketmq_demo.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
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
@RocketMQMessageListener(topic = "pay_check_16", consumerGroup = "consumer_grp_05")
public class MyRocketPayListener implements RocketMQListener<String> {

    @Autowired
    private ShopService shopService;


    @Autowired
    private RedisHolder redisHolder;


    @Override
    public void onMessage(String orderNum) {

        // 模拟支付状态检查，如支付成功，修改订单状态，如未支付修改订单状态并增加库存
        log.info("订单支付状态检查：" + orderNum + "订单状态为" + redisHolder.get(orderNum));
        if ("no".equals(redisHolder.get(orderNum))) {
            log.info("用户未完成支付,库存增加");
            shopService.addStock();
        }

    }
}
