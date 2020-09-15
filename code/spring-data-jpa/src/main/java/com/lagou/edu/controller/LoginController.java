package com.lagou.edu.controller;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName ResumeController
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-07 17:24
 * @Version V1.0
 **/
@Controller
public class LoginController {

    @Autowired
    private IResumeService resumeService;

    @RequestMapping("login")
    public String login(HttpServletRequest request, String username, String password) {

        HttpSession session = request.getSession();
        if (!StringUtils.isEmpty(session.getAttribute("user"))) {
            List<Resume> all = resumeService.findAll();
            request.setAttribute("resumeList", all);
            return "redirect:/resume";
        }
        if ("admin".equals(username) && "admin".equals(password)) {
            //获取简历信息 带到请求域中
            List<Resume> all = resumeService.findAll();
            session.setAttribute("user", "admin");
            request.setAttribute("resumeList", all);
            return "redirect:/resume";
        } else {
            return "login";
        }
    }


    @RequestMapping("resume")
    public String resume(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!StringUtils.isEmpty(session.getAttribute("user"))) {
            List<Resume> all = resumeService.findAll();
            request.setAttribute("resumeList", all);
            return "resume";
        } else {
            return "login";
        }
    }
}
