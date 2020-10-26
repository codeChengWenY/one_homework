package com.lagou.boot.config;

import com.lagou.boot.NettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: NettyServerConfig <br/>
 * Description: <br/>
 * date: 2020-05-19 17:54<br/>
 *
 * @author colde<br />
 */
//@Import(ZkConfig.class)
@Configuration
public class NettyServerConfig {

    @Bean
    public NettyServer nettyServer1(){

      return build("localhost",8000);

    }

    @Bean
    public NettyServer nettyServer2(){

        return build("localhost",8001);

    }
    private NettyServer build(String host, int port){
        NettyServer nettyServer = new NettyServer();
        nettyServer.setNettyServerHostName(host);
        nettyServer.setNettyServerPort(port);
        return nettyServer;

    }


}
