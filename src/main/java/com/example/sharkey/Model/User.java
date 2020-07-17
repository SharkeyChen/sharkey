package com.example.sharkey.Model;

public class User {
    private int id;

    private String username;

    private String password;

    private String nickname;

    private String sysnopsis;

    private String profile;

    public int getId(){return id;}

    public String getUsername(){return username;}

    public String getPassword(){return password;}

    public String getNickname(){return nickname;}

    public String getSysnopsis(){return sysnopsis;}

    public String getProfile(){return profile;}

    public void EncodePass(){
        this.password = "";
    }

    public void Print(){
        System.out.println("username:" + this.getUsername() + "\n"
                            + "password:" + this.getPassword());
    }
}
