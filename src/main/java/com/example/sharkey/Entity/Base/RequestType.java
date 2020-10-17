package com.example.sharkey.Entity.Base;

public enum RequestType {
    GET("Get",1),
    POST("Post", 2),
    PUT("Put", 3),
    DELETE("Delete", 4);

    private String name;
    private int index;

    RequestType(String name, int index){
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString(){
        return "name:" + this.name + "& index:" + this.index;
    }
}