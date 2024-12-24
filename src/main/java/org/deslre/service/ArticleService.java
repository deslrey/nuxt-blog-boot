package org.deslre.service;

import org.deslre.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.result.Results;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-12-24
 */
public interface ArticleService extends IService<Article> {

    Results<List<Article>> getArticles();

    Results<String> getData(Integer id);

    Results<Article> getArticle(Integer id);
}
