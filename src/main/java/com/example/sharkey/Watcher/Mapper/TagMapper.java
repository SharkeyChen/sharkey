package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.ArticleTag;
import com.example.sharkey.Entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    List<Tag> getTagListByTitle(String title);

    boolean insertTagList(List<Tag> list);

    boolean insertMapperForArticleTag(List<ArticleTag> list);

    Integer deleteMapperForArticleTag(int articleId);

    boolean deleteMapperByList(List<ArticleTag> list);
}
