package com.lagou.client;

import com.lagou.zk.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ClassName: ConnecManager <br/>
 * Description: <br/>
 * date: 2020-05-19 16:13<br/>
 *
 * @author colde<br />
 */
@Component
public class ServiceDiscovery {

    @Autowired
    private CuratorFramework zkClient;


    @Autowired
    private ConnectManager connectManager;

    @PostConstruct
    public void init() {
        watchNode();
    }

    private void watchNode() {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, ZkConfig.SERVER_REGISTRY_PATH, true);
        pathChildrenCache.getListenable().addListener((curatorFramework, event) -> {
            ChildData nodeData = event.getData();
            System.out.println("节点变更");
            if (nodeData != null) {
                PathChildrenCacheEvent.Type type = event.getType();
                String data = new String(nodeData.getData());

                switch (type) {
                    case CHILD_ADDED:
                    case INITIALIZED:
                    case CONNECTION_RECONNECTED:
                        // 连接重新连接
                        // 初始化
                        // 新增节点
                        connectManager.addServer(data);
                        break;
                    case CHILD_REMOVED:
                    case CONNECTION_SUSPENDED:
                    case CONNECTION_LOST:
                        // 连接丢失
                        // 连接暂停
                        // 节点移除
                        connectManager.removeServer(data);
                        break;
                    case CHILD_UPDATED:
                        // 节点更新
                        connectManager.removeServer(data);
                        connectManager.addServer(data);
                        break;

                }

                System.out.println("Receive event: "
                        + "type=[" + event.getType() + "]"
                        + ", path=[" + nodeData.getPath() + "]"
                        + ", data=[" + new String(nodeData.getData()) + "]"
                        + ", stat=[" + nodeData.getStat() + "]");

            }

        });
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
