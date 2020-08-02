package com.example.sharkey.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {
    private int id;

    private String title;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date publishtime;

    private String content;

    private String subtitle;

    private String html;

    private String author;

    private List<Tag> tags;

    public List<Tag> getTags(){return this.tags;}

    public String getHtml(){return html;}

    public String getAuthor(){return author;}

    public int getId(){return id;}

    public String getTitle(){return title;}

    public Date getPublishTime(){return publishtime;}

    public String getContent(){return content;}

    public String getSubtitle(){return subtitle;}

    public void setPublishtime(Date date){
        this.publishtime = date;
    }

    public List<Tag> BgetNewTagList(){
        List<Tag> list = new ArrayList<Tag>();
        for(Tag t : tags){
            if(t.getId() == -1){
                list.add(t);
            }
        }
        return list;
    }
}
