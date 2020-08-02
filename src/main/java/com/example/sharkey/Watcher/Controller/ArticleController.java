package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.Article;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Watcher.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/list")
    public RespPageBean getArticleByPage(){
        return articleService.getArticleByPage();
    }

    @PostMapping("/add")
    public RespBean addArticle(@RequestBody Article article){
        article.setPublishtime(new Date());
        return articleService.addArticle(article);
    }
}
