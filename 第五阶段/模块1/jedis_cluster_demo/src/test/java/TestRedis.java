import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TestRedis
 * @Description:
 * @Author CoderCheng
 * @Date 2021-01-14 15:57
 * @Version V1.0
 **/
public class TestRedis {


    private static JedisCluster jedisCluster;

    static {
        // 添加集群的服务节点Set集合
        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
        // 添加节点
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7001));
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7002));
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7003));
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7004));
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7005));
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7006));
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7007));
        hostAndPortsSet.add(new HostAndPort("117.50.7.7", 7008));

        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        jedisCluster = new JedisCluster(hostAndPortsSet, jedisPoolConfig);
    }


    /**
     * 测试key:value数据
     * 集群中flushDB、keys废弃
     */
    public static void main(String[] args) throws Exception {
        //System.out.println("清空数据："+jedis.flushDB());
        System.out.println("判断某个键是否存在：" + jedisCluster.exists("username"));
        System.out.println("新增<'username','wukong'>的键值对：" + jedisCluster.set("username", "xiaohai"));
        System.out.println("是否存在:" + jedisCluster.exists("username"));
        System.out.println("新增<'password','password'>的键值对：" + jedisCluster.set("password", "123456"));
        //Set<String> keys = jedis.keys("*");
        // System.out.println("系统中所有的键如下："+keys);
        System.out.println("删除键password:" + jedisCluster.del("password"));
        System.out.println("判断键password是否存在：" + jedisCluster.exists("password"));
        System.out.println("设置键username的过期时间为10s:" + jedisCluster.expire("username", 10));
        TimeUnit.SECONDS.sleep(2); // 线程睡眠2秒System.out.println("查看键username的剩余生存时间："+jedis.ttl("username"));
        System.out.println("查看键username的剩余生存时间：" + jedisCluster.ttl("username"));
        System.out.println("移除键username的生存时间：" + jedisCluster.persist("username"));
        System.out.println("查看键username的剩余生存时间：" + jedisCluster.ttl("username"));
        System.out.println("查看键username所存储的值的类型：" + jedisCluster.type("username"));
    }

}
