package com.lagou.service.impl;

import com.lagou.mapper.ArticleMapper;
import com.lagou.pojo.Article;
import com.lagou.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ArticleImpl
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-23 14:31
 * @Version V1.0
 **/
@Service
public class ArticleImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public List<Article> selectArticleByPage() {
        return articleMapper.selectArticle();
    }
}
