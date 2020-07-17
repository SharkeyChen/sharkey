package com.example.sharkey.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Bug {
    private int id;

    private String content;

    private boolean isDeal;

    private String author;

    private String dealer;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String profile;

    public String getProfile(){return profile;}

    public int getId(){return id;}

    public String getContent(){return content;}

    public boolean getIsDeal(){return isDeal; }

    public String getAuthor(){return author;}

    public String getDealer(){return dealer;}

    public Date getCreateTime(){return createTime;}

    public void setCreateTime(){this.createTime = new Date();}
}
