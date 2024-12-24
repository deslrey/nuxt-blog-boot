package org.deslre.controller;


import org.deslre.entity.Article;
import org.deslre.result.Results;
import org.deslre.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-12-24
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("getArticles")
    public Results<List<Article>> getArticles() {
        return articleService.getArticles();
    }

    @PostMapping("getId")
    public Results<String> getData(Integer id) {
        return articleService.getData(id);
    }

    @PostMapping("getArticle")
    public Results<Article> getArticle(Integer id) {
        return articleService.getArticle(id);
    }

    @GetMapping("getList")
    public Results<List<Article>> getList() {
        return articleService.getList();
    }


    @PostMapping("updateExist")
    public Results<Void> updateExist(Integer id, Boolean exist) {
        return articleService.updateExist(id, exist);
    }

    @PostMapping("addArticle")
    public Results<Void> addArticle(Article article) {
        return articleService.addArticle(article);
    }

    @PostMapping("updateArticle")
    public Results<Void> updateArticle(Article article) {
        return articleService.updateArticle(article);
    }

}
