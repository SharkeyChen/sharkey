package com.example.sharkey.Entity;

import java.util.List;

public class User {
    private int id;

    private String username;

    private String password;

    private String nickname;

    private String sysnopsis;

    private String profile;

    private String console;

    private String ip;

    private String email;

    private boolean checkEmail;

    private List<UserSocial> userSocials;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserSocial> getUserSocials() {
        return userSocials;
    }

    public int getId(){return id;}

    public String getUsername(){return username;}

    public String getPassword(){return password;}

    public String getNickname(){return nickname;}

    public String getSysnopsis(){return sysnopsis;}

    public String getProfile(){return profile;}

    public String getConsole(){return console;}

    public void EncodePass(){
        this.password = "";
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCheckEmail(boolean checkEmail) {
        this.checkEmail = checkEmail;
    }

    public boolean getCheckEmail(){return checkEmail;}

    public void Print(){
        System.out.println("username:" + this.getUsername() + "\n"
                            + "password:" + this.getPassword());
    }

}
