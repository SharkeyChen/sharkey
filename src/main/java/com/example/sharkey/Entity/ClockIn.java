package com.example.sharkey.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Author: Sharkey
 * Date: 2020/8/17
 */


public class ClockIn {
    private int id;

    private String username;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+08")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.username = userid;
    }
}
