package cn.codercheng.rocketmq_demo.config;

import redis.clients.jedis.Jedis;

/**
 * @ClassName RedisConfig
 * @Description:
 * @Author CoderCheng
 * @Date 2021-03-01 10:08
 * @Version V1.0
 **/
public class RedisConfig {


    public static Jedis jedis(){
        Jedis jedis = new Jedis("localhost");
        return jedis;
    }
}
