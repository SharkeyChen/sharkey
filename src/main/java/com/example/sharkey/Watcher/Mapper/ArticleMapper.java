package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    List<Article> getAllArticle();

    boolean addArticle(Article article);

    List<Article> getArticleListById(@Param("username")String username);

    Article getArticleById(int id, @Param("author")String author);

    boolean deleteArticle(int articleId);

    boolean updateArticle(Article article);
}
