package cn.codercheng.config;

import cn.codercheng.controller.MessageController;
import cn.codercheng.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName MyMessageListener
 * @Description:
 * @Author CoderCheng
 * @Date 2021-01-25 17:32
 * @Version V1.0
 **/

@Slf4j
@Component
public class MyMessageListener {


    @RabbitListener(queues = "queue.dlx.shopping")
    public void getMessage(long id) throws IOException {
        log.info("===========收到过期消息========== orderId=" + id);
        final Order order = MessageController.orders.get(id);
        if(!"已支付".equals(order.getStatus())){
            order.setStatus("已过期");
        }else{
            System.out.println("该已支付过,订单不会取消");

        }
    }
}
