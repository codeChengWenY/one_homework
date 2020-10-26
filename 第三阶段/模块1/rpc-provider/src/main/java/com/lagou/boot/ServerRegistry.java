package com.lagou.boot;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.lagou.zk.ZkConfig.SERVER_REGISTRY_PATH;

/**
 * ClassName: ServerRegistry <br/>
 * Description: <br/>
 * date: 2020-05-19 15:05<br/>
 *
 * @author colde<br />
 */
@Component
public class ServerRegistry {


    @Autowired
    private CuratorFramework zkClient;


    /**
     * 服务器注册
     * @param host
     * @param port
     */
    public void registry(String host, int port){
        try {
            this.zkClient.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(SERVER_REGISTRY_PATH+"/"+host,(host+":"+port).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
