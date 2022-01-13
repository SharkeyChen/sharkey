package com.example.sharkey.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VoteConfig {
    private int id;

    private int uid;

    private boolean enable;

    private String token;

    private User user;

    private String address;

    private double lat;

    private double lng;

    private String apartment;

    private String province;

    private String first, second, third;

    public boolean getEnable(){
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
