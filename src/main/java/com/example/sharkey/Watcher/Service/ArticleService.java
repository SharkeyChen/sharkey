package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.*;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.ArticleMapper;
import com.example.sharkey.Watcher.Mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public RespPageBean getArticleByPage(){
        RespPageBean bean = new RespPageBean();
        List<Article> list = articleMapper.getAllArticle();
        bean.setData(list);
        bean.setTotal(Long.parseLong(String.valueOf(list.size())));
        return bean;
    }

    public RespBean addArticle(Article article){
        MyLogger.logger(article.getTags().toString());
        String status = "";
        if(articleMapper.addArticle(article)){
            status += "Article上传成功";
            int id = article.getId();
            List<Tag> list = article.BgetNewTagList();
            if(list.size() != 0) {
                if (tagMapper.insertTagList(list)) {
                    status += ",Tags添加成功";
                }
                else{
                    status += ",Tags添加失败";
                    return RespBean.ok(status);
                }
            }
            List<ArticleTag> nlist = new ArrayList<>();
            for (Tag t : article.getTags()) {
                ArticleTag at = new ArticleTag();
                at.setArticleid(article.getId());
                at.setTagid(t.getId());
                nlist.add(at);
            }
            if (tagMapper.insertMapperForArticleTag(nlist)) {
                status += ",映射添加成功";
                return RespBean.ok(status);
            }
            else {
                status += ",映射添加失败";
                return RespBean.ok(status);
            }
        }
        return RespBean.error("添加失败");
    }
}
