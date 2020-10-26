package com.lagou.service;

import com.lagou.client.NettyClient;
import com.lagou.proxy.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * ClassName: RpcServiceConfig <br/>
 * Description: <br/>
 * date: 2020-05-19 17:50<br/>
 *
 * @author colde<br />
 */
@Configuration
public class RpcServiceConfig {

    @Autowired
    private NettyClient nettyClient;

    @Bean
    public IUserService getUserService(){

        try {
            IUserService userService = ProxyFactory.create(IUserService.class,nettyClient);
            return userService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
