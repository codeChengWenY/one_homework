package com.lagou.edu.controller;

import com.lagou.edu.controller.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @ClassName CoderController
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-23 17:20
 * @Version V1.0
 **/
@RestController
public class CodeController {


    @Autowired
    private IEmailService iEmail;


    /**
     * @Description: 发送邮箱验证码
     * @Param: [email, code]
     * @Return: boolean
     * @Author: CoderCheng
     * @Date: 2020/11/23
     * @Time: 16:32
     **/
    @RequestMapping("/code/create/{email}")
    public boolean sendEmail(@PathVariable("email") String email) {
        return iEmail.sendEMail(email, generateRandomByScope(000000, 999999) + "");
    }


    /**
     * @Description: 发送邮箱验证码
     * @Param: [email, code]
     * @Return: boolean
     * @Author: CoderCheng
     * @Date: 2020/11/23
     * @Time: 16:32
     **/
    @RequestMapping("/code/validate/{email}/{code}")
    public Integer verifyEmailCode(@PathVariable("email") String email, @PathVariable("code") String code) {
        return iEmail.verifyEmailCode(email, code);
    }






    public static int generateRandomByScope(int small, int bignum) {
        int num = -1;
        Random random = new Random();
        num = random.nextInt(bignum) + small;  //产生幸运数
        return num;
    }


    public static void main(String[] args) {
        int i = generateRandomByScope(000000, 999999);
        System.out.println(i);


    }

}
