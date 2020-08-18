package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> getAllArticle();

    boolean addArticle(Article article);

    List<Article> getArticleListById(String username);

    boolean deleteArticle(int articleId);

    boolean updateArticle(Article article);
}
