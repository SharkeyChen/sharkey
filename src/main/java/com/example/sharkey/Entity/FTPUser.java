package com.example.sharkey.Entity;

public class FTPUser {
    private String userid;

    private String userpassword;

    private String homedirectory;

    public void setHomedirectory(String homedirectory) {
        this.homedirectory = homedirectory;
    }

    public String getHomedirectory() {
        return homedirectory;
    }

    public String getUserid() {
        return userid;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
