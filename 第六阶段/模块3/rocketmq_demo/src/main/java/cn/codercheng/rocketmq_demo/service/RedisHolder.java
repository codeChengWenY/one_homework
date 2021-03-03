package cn.codercheng.rocketmq_demo.service;

import cn.codercheng.rocketmq_demo.config.RedisConfig;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @ClassName RedisHolder
 * @Description:
 * @Author CoderCheng
 * @Date 2021-03-01 10:06
 * @Version V1.0
 **/
@Service
public class RedisHolder {


    public  static  String  ORDER="order:";

    public RedisHolder() {
        release();
    }



    public void getLock(){
        Jedis jedis = RedisConfig.jedis();
        Long lock = jedis.incr("lock");
        System.out.println("lock："+lock);
        while (lock!=1){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock = jedis.incr("lock");
        }
    }

    public void release(){
        RedisConfig.jedis().set("lock","0");
    }


    public void set(String  orderId , String value){
        RedisConfig.jedis().set(ORDER+orderId,value);
    }



    public String get(String  orderId ){
       return  RedisConfig.jedis().get(ORDER+orderId);
    }

}
