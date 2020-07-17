package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Model.ArticleTag;
import com.example.sharkey.Model.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    public List<Tag> getTagListByTitle(String title);

    public boolean insertTagList(List<Tag> list);

    public boolean insertMapperForArticleTag(List<ArticleTag> list);
}
