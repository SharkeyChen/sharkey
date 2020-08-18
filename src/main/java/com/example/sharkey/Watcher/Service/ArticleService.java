package com.example.sharkey.Watcher.Service;

/**
 * Author: Sharkey
 * Date: 2020/8/2
 */

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
                if(t.getStatus() != Tag.DELETE) {
                    ArticleTag at = new ArticleTag();
                    at.setArticleid(article.getId());
                    at.setTagid(t.getId());
                    nlist.add(at);
                }
            }
            if(nlist.size() != 0){
                if(tagMapper.insertMapperForArticleTag(nlist)) {
                    status += ",映射添加成功";
                    return RespBean.ok(status);
                }
                else {
                    status += ",映射添加失败";
                    return RespBean.error(status);
                }
            }
            else{
                status += ",添加映射成功";
                return RespBean.ok(status);
            }
        }
        return RespBean.error("添加失败");
    }

    public RespPageBean getArticleListById(String username){
        RespPageBean bean = new RespPageBean();
        List<Article> data = articleMapper.getArticleListById(username);
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespBean deleteArticle(Article article){
        try{
            articleMapper.deleteArticle(article.getId());
            tagMapper.deleteMapperForArticleTag(article.getId());
        }catch (Exception e){
            MyLogger.logger(e.getMessage());
            return RespBean.error("删除失败");
        }
        return RespBean.ok("删除成功");
    }

    public RespBean updateArticle(Article article){
        try{
            articleMapper.updateArticle(article);
            List<ArticleTag> list = new ArrayList();
            for(Tag t : article.getTags()){
                if(t.getStatus() == Tag.DELETE){
                    ArticleTag at = new ArticleTag();
                    at.setArticleid(article.getId());
                    at.setTagid(t.getId());
                    list.add(at);
                }
            }
            if(list.size() != 0){
                tagMapper.deleteMapperByList(list);
            }
            List<Tag> tlist = article.BgetNewTagList();
            if(tlist.size() != 0){
                tagMapper.insertTagList(tlist);
            }
            list.clear();
            for(Tag t : article.getTags()){
                if(t.getStatus() == Tag.INSERT){
                    ArticleTag at = new ArticleTag();
                    at.setArticleid(article.getId());
                    at.setTagid(t.getId());
                    list.add(at);
                }
            }
            if(list.size() != 0){
                tagMapper.insertMapperForArticleTag(list);
            }
        }catch (Exception e){
            MyLogger.logger(e.getMessage());
            return RespBean.error("更新失败");
        }
        return RespBean.ok("更新成功");
    }
}
