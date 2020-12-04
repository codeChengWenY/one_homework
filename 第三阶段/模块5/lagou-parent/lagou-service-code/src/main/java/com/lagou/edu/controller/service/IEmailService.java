package com.lagou.edu.controller.service;

/**
 * @ClassName IEmailService
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-24 11:54
 * @Version V1.0
 **/
public interface IEmailService {


    public boolean sendEMail(String email, String code);



    public Integer  verifyEmailCode (String email, String code);
}
