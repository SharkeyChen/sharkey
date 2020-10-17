package com.example.sharkey.Entity;

public class VoteConfig {
    private int id;

    private int uid;

    private boolean enable;

    private String token;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public boolean getEnable(){
        return enable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
