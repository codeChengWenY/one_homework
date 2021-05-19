package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvcframework.annotations.LagouAutowired;
import com.lagou.edu.mvcframework.annotations.LagouController;
import com.lagou.edu.mvcframework.annotations.LagouRequestMapping;
import com.lagou.edu.mvcframework.annotations.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@LagouController
@LagouRequestMapping("/bothauth")
@Security(value = {"张三", "lisi"})
public class BothALLController {


    @LagouAutowired
    private IDemoService demoService;


    /**
     * URL: /bothauth/query?username=lisi
     *
     * @param request
     * @param response
     * @param username
     * @return
     */
    @LagouRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.get(username);
    }


    /**
     * URL: /bothauth/all?username=张三
     *
     * @param request
     * @param response
     * @param username
     * @return
     */
    @LagouRequestMapping("/all")
    public String auth(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.get(username);
    }


    /**
     * URL: /bothauth/both?username=张三
     *
     * @param request
     * @param response
     * @param username
     * @return
     */
    @LagouRequestMapping("/both")
    public String lisi(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.get(username);
    }

}
