package com.lagou.edu.service;

/**
 * @ClassName UserService
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-27 11:33
 * @Version V1.0
 **/
public interface UserService {

    boolean register(String email, String password);

    boolean isRegister(String email);

    String login(String email, String password);

}
