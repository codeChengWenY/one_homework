package com.lagou.web;

import com.lagou.client.ConnectManager;
import com.lagou.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * ClassName: UserController <br/>
 * Description: <br/>
 * date: 2020-05-19 17:44<br/>
 *
 * @author colde<br />
 */
@RestController
public class ConsumerController {


    @Autowired
    private IUserService userService;


    @Autowired
    private ConnectManager connectManager;


    @GetMapping(value = "/")
    public String info() {
        Map<String, ConnectManager.LoadBalanceStrategy> channels = connectManager.getChannels();
        if (channels.size() == 0) {
            return "当前没有可用的服务器";
        }
        Set<String> hosts = channels.keySet();
        String result = "当前存活服务器：</br> ";
        for (String host : hosts) {
            result += "[ " + host + " ] </br>";
        }
        return result;
    }


    @GetMapping(value = "/show")
    public String show(String name) {
        return userService.sayHello(name);
    }


}
