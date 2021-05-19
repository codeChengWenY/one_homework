package com.lagou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.pojo.Article;
import com.lagou.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class ArticleController {


    @Autowired
    private IArticleService articleService;


    @RequestMapping("/blog")
    public String blog(Model model, Integer page) {

        int pageSize = 2;
        if (Objects.isNull(page)) {
            page = 1;
        }
        PageHelper.startPage(page, pageSize);
        List<Article> articles = articleService.selectArticleByPage();
        PageInfo<Article> pageArticle = new PageInfo<>(articles);

        // 如果下一页是0 直接跳到尾页不循环
        if (0 == pageArticle.getNextPage()) {
            pageArticle.setNextPage(pageArticle.getPages());
        }
        // 如果上一页是0 直接跳到首页页不循环
        if (0 == pageArticle.getPrePage()) {
            pageArticle.setPrePage(pageArticle.getStartRow());
        }
        model.addAttribute("pageArticle", pageArticle);
        return "client/index";
    }


}
