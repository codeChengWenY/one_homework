package cn.codercheng.rocketmq_demo.controller;

import cn.codercheng.rocketmq_demo.service.RedisHolder;
import cn.codercheng.rocketmq_demo.service.ShopService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName RocketMqController
 * @Description:
 * @Author CoderCheng
 * @Date 2021-02-26 15:40
 * @Version V1.0
 **/

@RestController
public class RocketMqController {


    //存储订单信息
    public static ConcurrentHashMap<String, AtomicBoolean> orderMap = new ConcurrentHashMap<String, AtomicBoolean>();

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ShopService shopService;

    @Autowired
    private RedisHolder redisHolder;

    @RequestMapping("order")
    public void send() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 500; i++) {
            executorService.execute(() -> {
                // 获取锁
                redisHolder.getLock();
                if (shopService.hasStock()) {
                    String orderId = UUID.randomUUID().toString();
                    redisHolder.set(orderId, "no");
                    rocketMQTemplate.convertAndSend("tp_order_pay_15", orderId);
                }
                //释放锁
                redisHolder.release();
            });
        }
        ;

    }

    ;

    @RequestMapping("pay")
    public void pay(String orderId) {

        //库存减
        shopService.dbOrder();
        redisHolder.set(orderId, "yes");

    }

//    public boolean order() {
//        boolean flag = false;
//        RLock lock = redisson.getLock(lockKey);
//        try {
//            //lock.lockAsync(5 , TimeUnit.SECONDS);
//            //lock.lock(5, TimeUnit.SECONDS); //设置60秒自动释放锁  （默认是30秒自动过期）
//            Future<Boolean> res = lock.tryLockAsync(100, 5, TimeUnit.SECONDS);
//            boolean result = res.get();
//            System.out.println("result:" + result);
//            if (result) {
//                int stock = Integer.parseInt(redisTemplate.opsForValue().get(product1Count).toString());
//                if (stock > 0) {
//                    redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
//                    flag = true;
//                } else {
//                    flag = false;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock(); //释放锁
//        }
//        return flag;
//    }


}
