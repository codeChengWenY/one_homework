package com.lagou.edu.controller;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName BaseController
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-08 12:12
 * @Version V1.0
 **/
@Controller
public class BaseController {


    @Autowired
    private IResumeService resumeService;


    public  void addResumeInfo(HttpServletRequest request) {
        List<Resume> all = resumeService.findAll();
        request.setAttribute("resumeList", all);

    }
}
