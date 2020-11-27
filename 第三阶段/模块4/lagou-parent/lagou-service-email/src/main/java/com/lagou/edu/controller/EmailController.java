package com.lagou.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName EmailController
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-23 14:50
 * @Version V1.0
 **/
@RestController
public class EmailController {


    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * @Description: 发送邮箱验证码
     * @Param: [email, code]
     * @Return: boolean
     * @Author: CoderCheng
     * @Date: 2020/11/23
     * @Time: 16:32
     **/
    @RequestMapping("/email/{email}/{code}")
    public boolean sendEmail(@PathVariable("email") String email, @PathVariable("code") String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮箱验证码");
        message.setText(code);
        message.setTo(email);
        message.setFrom("1042732167@qq.com");
        javaMailSender.send(message);
        return true;
    }

}
