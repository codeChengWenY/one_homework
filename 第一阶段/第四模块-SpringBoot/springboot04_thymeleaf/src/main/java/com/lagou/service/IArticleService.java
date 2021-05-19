package com.lagou.service;

import com.lagou.pojo.Article;

import java.util.List;

/**
 * @ClassName IArticle
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-23 14:30
 * @Version V1.0
 **/
public interface IArticleService {



    public List<Article> selectArticleByPage();
}
