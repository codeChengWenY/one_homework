package cn.codercheng.controller;

import cn.codercheng.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MessageController
 * @Description:
 * @Author CoderCheng
 * @Date 2021-01-25 17:04
 * @Version V1.0
 **/

@Slf4j
@RestController
public class MessageController {


    @Autowired
    private AmqpTemplate rabbitTemplate;


    public static Map<Long, Order> orders = new HashMap<>();


    @PostMapping(value = "/order")
    public ResponseEntity createOrder() throws UnsupportedEncodingException {

        Order order = new Order();
        order.setOrderId(System.currentTimeMillis());
        order.setStatus("待支付");
        orders.put(order.getOrderId(), order);
        rabbitTemplate.convertAndSend("ex.shopping", "key.shopping", order.getOrderId());
        return ResponseEntity.ok(order);
    }


    @PostMapping(value = "/pay")
    public ResponseEntity pay(long id) throws UnsupportedEncodingException {

        final Order order = orders.get(id);

        if (order.getStatus().equals("已过期")) {
            log.info("===========支付订单失败，订单已过期========== orderId=" + id);
            return ResponseEntity.ok().body("支付失败，订单已过期");
        }
        log.info("-----订单支付成功---------");
        order.setStatus("已支付");
        return ResponseEntity.ok().body("支付成功");
    }


    @GetMapping(value = "/list")
    public ResponseEntity list(){
        final Collection<Order> values = orders.values();
        values.removeIf(order ->order.getStatus().equals("已支付"));
        return ResponseEntity.ok(values);
    }

}
