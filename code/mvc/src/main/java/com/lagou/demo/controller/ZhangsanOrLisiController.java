package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LagouController
@LagouRequestMapping("/one")
public class ZhangsanOrLisiController {

    @LagouAutowired
    private IDemoService demoService;

    /**
     * URL: /one/zhansanquery?username=张三
     *
     * @param request
     * @param response
     * @param username
     * @return
     */
    @LagouRequestMapping("/zhansanquery")
    @Security(value = {"张三"})
    public String zhangsanQuery(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.get(username);
    }


    /**
     * URL: /auth/lisi?username=张三lisi
     *
     * @param request
     * @param response
     * @param username
     * @return
     */
    @LagouRequestMapping("/lisiquery")
    @Security(value = {"lisi"})
    public String lisiquery(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.get(username);
    }

}
