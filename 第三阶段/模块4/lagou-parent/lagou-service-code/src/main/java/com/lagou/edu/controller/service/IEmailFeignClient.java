package com.lagou.edu.controller.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName IEmail
 * @Description:
 * @Author CoderCheng
 * @Date 2020-11-23 17:25
 * @Version V1.0
 **/

@FeignClient(value = "lagou-service-email",path = "email")
public interface IEmailFeignClient {


    @RequestMapping("/{email}/{code}")
    public boolean sendEmail(@PathVariable("email") String email, @PathVariable("code") String code);


}
