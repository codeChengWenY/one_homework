package com.coder.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName JDBCConfig
 * @Description:
 * @Author CoderCheng
 * @Date 2020-10-22 16:22
 * @Version V1.0
 **/
@Component
public class ZookeeperJDBCConfig {


    private static ZkClient zkClient;

    private static ObjectMapper mapper = new ObjectMapper();


    private static DruidDataSource druidDataSource;

    public static final String SERVER_REGISTRY_PATH = "/jdbc";


    @PostConstruct
    public static void initZk() {
        System.out.println("zookeeper初始化了");
        zkClient = new ZkClient("127.0.0.1:2181");

        zkClient.setZkSerializer(new ZkStrSerializer());
        boolean exists = zkClient.exists(SERVER_REGISTRY_PATH);
        if (!exists) {
            zkClient.createPersistent(SERVER_REGISTRY_PATH,new JDBCConfig());
        }

        //配置 数据库连接池
        configDruidSource();

        zkClient.subscribeDataChanges("/jdbc", new IZkDataListener() {

            public void handleDataChange(String path, Object data) {

                System.out.println(path + " data is changed, new data " + data);

                druidDataSource.close();
                configDruidSource();
            }

            public void handleDataDeleted(String path) {

                System.out.println(path + " is deleted!!");

                druidDataSource.close();
            }
        });
    }


    private static JDBCConfig getJDBCConfig() {

        Object data = zkClient.readData("/jdbc");

        try {
            JDBCConfig myConfig = mapper.readValue(data.toString(), JDBCConfig.class);

            System.out.println(myConfig.toString());

            return myConfig;

        } catch (JsonProcessingException e) {

            return new JDBCConfig();
        }
    }


    /**
     * 配置数据库连接池
     * <p>
     * 1. 从 zookeeper 中获取配置信息
     * 2. 更新 Druid 配置
     * 3. 执行测试 sql
     */

    public static void configDruidSource() {

        JDBCConfig myConfig = getJDBCConfig();
        updateDruidConfig(myConfig);
        try {
            executeTestSQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void updateDruidConfig(JDBCConfig myConfig) {
        druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(myConfig.getUrl());
        druidDataSource.setUsername(myConfig.getUsername());
        druidDataSource.setPassword(myConfig.getPassword());
        druidDataSource.setDriverClassName(myConfig.getDriver());
    }


    private static void executeTestSQL() throws SQLException {

        Connection connection = druidDataSource.getConnection();

        PreparedStatement pst = connection.prepareStatement("SELECT id, name  FROM user;");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            System.out.println("id : " + rs.getString(1) + " , name : " + rs.getString(2));
        }
    }


}
