package com.lagou.edu.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import service.EmailSendService;

/**
 * @ClassName EmailSendServiceImpl
 * @Description:
 * @Author CoderCheng
 * @Date 2020-12-03 13:32
 * @Version V1.0
 **/
@Service
public class EmailSendServiceImpl implements EmailSendService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendEmail(String email, String code) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮箱验证码");
        message.setText(code);
        message.setTo(email);
        message.setFrom("1042732167@qq.com");
        javaMailSender.send(message);
        return true;
    }
}
