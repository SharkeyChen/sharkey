package com.example.sharkey.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Author: Sharkey
 * Date: 2020/8/17
 *
 */
public class Memo {
    private int id;

    private String username;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+08")
    private Date date;

    private String memo;

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getMemo() {
        return memo;
    }

    public String getUsername() {
        return username;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
