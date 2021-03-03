package cn.codercheng.rocketmq_demo.service;

import cn.codercheng.rocketmq_demo.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShopService
 * @Description:
 * @Author CoderCheng
 * @Date 2021-03-01 10:09
 * @Version V1.0
 **/
@Service
public class ShopService {

    private Integer dbStockSize = 100;

    @Autowired
    private RedisHolder redisHolder;

    public ShopService() {
        RedisConfig.jedis().set("orderSize", "0");
    }

    public boolean hasStock() {
        Integer orderSize = Integer.valueOf(RedisConfig.jedis().get("orderSize"));
        if (orderSize < 100) {
            RedisConfig.jedis().incr("orderSize");
            return true;
        }
        return false;
    }

    public void addStock() {
//        Integer orderSize = Integer.valueOf(RedisConfig.jedis().get("orderSize"));
//        orderSize -= 1;
//        System.out.println("》》》》》》》》》"+orderSize);
        RedisConfig.jedis().decr("orderSize");

      //  RedisConfig.jedis().set("orderSize", orderSize + "");
        dbStockSize++;
    }

    public void dbOrder() {
        dbStockSize--;
    }
}
