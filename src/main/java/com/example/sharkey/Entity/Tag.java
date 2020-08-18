package com.example.sharkey.Entity;

public class Tag {
    public static int DEFAULT = 0;

    public static int DELETE = 1;

    public static int INSERT = 2;

    private int id;

    private String tag;

    private int status = this.DEFAULT;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus(){return this.status;}

    public int getId(){return this.id;}

    public String getTag(){return this.tag;}

    public void setTag(String tag){this.tag = tag;}
}
