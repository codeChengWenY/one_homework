package com.lagou.edu.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserServiceImpl
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-27 11:34
 * @Version V1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    private Map<String, String> users = new ConcurrentHashMap();


    public UserServiceImpl() {
        users.put("111@qq.com", "111");
    }


    @Override
    public boolean register(String email, String password) {

        users.put(email, password);
        return true;
    }

    @Override
    public boolean isRegister(String email) {
        return users.containsKey(email);
    }

    @Override
    public String login(String email, String password) {
        if (isRegister(email)) {
            String dbPassword = users.get(email);
            if (StringUtils.equals(password, dbPassword)) {
                return email;
            } else {
                throw new RuntimeException("密码不正确");
            }
        } else {
            throw new RuntimeException("邮箱未注册");
        }
    }
}
