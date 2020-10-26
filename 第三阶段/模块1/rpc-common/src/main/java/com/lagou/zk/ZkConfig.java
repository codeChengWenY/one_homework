package com.lagou.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: ZkConfig <br/>
 * Description: <br/>
 * date: 2020-05-19 15:53<br/>
 *
 * @author colde<br />
 */
@Configuration
public class ZkConfig {

    private String zookeeperAddress = "localhost:2181";

    public static final String SERVER_REGISTRY_PATH = "/netty_services";

    @Bean
    public CuratorFramework zkClient(){

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zookeeperAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        try {
            initRootNode(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    private void initRootNode(CuratorFramework client) throws Exception {

        Stat stat = client.checkExists().forPath(SERVER_REGISTRY_PATH);
        if(stat == null){
            client.create().forPath(SERVER_REGISTRY_PATH);
        }

    }

}
