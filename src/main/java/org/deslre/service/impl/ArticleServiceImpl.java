package org.deslre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.deslre.entity.Article;
import org.deslre.mapper.ArticleMapper;
import org.deslre.result.Results;
import org.deslre.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-12-24
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Results<List<Article>> getArticles() {

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<Article>().eq(Article::getExist, true);
        List<Article> articleList = list(wrapper);
        if (articleList == null) {
            List<Article> list = new ArrayList<>();
            return Results.ok(list);
        }
        return Results.ok(articleList);
    }
}
