package com.example.sharkey.Entity;

public class IpFilter {
    public static int IP_ALLOW = 1;

    public static int IP_DENY = 0;

    private String ip;

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}
