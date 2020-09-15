package com.lagou.edu.controller;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName ResumeController
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-07 17:24
 * @Version V1.0
 **/
@Controller
@RequestMapping("resume")
public class ResumeController extends BaseController {


    @Autowired
    private IResumeService resumeService;

    @RequestMapping("list")
    @ResponseBody
    public List<Resume> resumeList() {
        return resumeService.findAll();

    }


    @RequestMapping("save")
    public String save(HttpServletRequest request, Resume resume) {
        resumeService.upadate(resume);
        addResumeInfo(request);
        return "redirect:/resume";

    }


    @RequestMapping("add")
    public String add(Resume resume) {
        return "addresume";

    }


    @RequestMapping("update")
    public String updateView(HttpServletRequest request, Long id) {
        request.setAttribute("resumes", resumeService.findone(id));
        return "updateresume";
    }


    @RequestMapping("edit")
    public String edit(HttpServletRequest request, Resume resume) {
        resumeService.upadate(resume);
        addResumeInfo(request);
        return "redirect:/resume";

    }


    @RequestMapping("del")
    public String del(HttpServletRequest request, Resume resume) {
        resumeService.del(resume);
        addResumeInfo(request);
        return "redirect:/resume";

    }
}
