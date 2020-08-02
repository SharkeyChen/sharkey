package com.example.sharkey.Entity;

public class ArticleTag {
    private int id;

    private int articleid;

    private int tagid;

    public int getId(){return this.id;}

    public int getArticleid(){return this.articleid;}

    public int getTagid(){return this.tagid;}

    public void setArticleid(int articleid){this.articleid = articleid;}

    public void setTagid(int tagid){this.tagid = tagid;}
}
