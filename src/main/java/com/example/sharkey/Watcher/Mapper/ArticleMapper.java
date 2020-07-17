package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Model.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    public List<Article> getAllArticle();

    public boolean addArticle(Article article);
}
