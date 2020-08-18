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

    @GetMapping("/getmylist")
    public RespPageBean getArticleListById(@RequestParam("username") String username){
        return articleService.getArticleListById(username);
    }

    @DeleteMapping("/delete")
    public RespBean deleteArticleById(@RequestBody Article article){
        return articleService.deleteArticle(article);
    }

    @PostMapping("/update")
    public RespBean updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }
}
