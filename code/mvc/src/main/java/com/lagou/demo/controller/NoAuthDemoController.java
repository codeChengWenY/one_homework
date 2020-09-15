package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LagouController
@LagouRequestMapping("/noauth")
public class NoAuthDemoController {


    @LagouAutowired
    private IDemoService demoService;


    /**
     * URL: /noauth/query?name=lisi
     *
     * @param request
     * @param response
     * @return
     */
    @LagouRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response ) {
        return "不拦截请求:"+request.getRequestURI()+"访问成功";
    }


}
