package org.deslre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.deslre.entity.Article;
import org.deslre.mapper.ArticleMapper;
import org.deslre.result.Results;
import org.deslre.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Results<String> getData(Integer id) {
        if (id == null) {
            return Results.fail("获取文章失败，请联系作者");
        }

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>()
                .eq(Article::getId, id)
                .eq(Article::getExist, true);

        Article article = getOne(queryWrapper);
        if (article == null) {
            return Results.fail("当前文章不存在");
        }

        try {
            // 使用绝对路径
            File file = new File(article.getStoragePath()); // 假设存储路径是绝对路径
            if (!file.exists() || !file.isFile()) {
                return Results.fail("文件不存在或路径不正确");
            }

            // 读取 Markdown 文件
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String markdown = reader.lines().collect(Collectors.joining("\n"));

                // 可选：将 Markdown 转换为 HTML
                Parser parser = Parser.builder().build();
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String html = renderer.render(parser.parse(markdown));

                return Results.ok(html);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Results.fail("读取文件失败");
        }
    }
}

