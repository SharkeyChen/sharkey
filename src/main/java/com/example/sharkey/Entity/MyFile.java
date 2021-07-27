package com.example.sharkey.Entity;

import java.util.Date;

public class MyFile {
    private String name;

    private long size;

    private long time;

    private boolean isDirection;

    public void setDirection(boolean direction) {
        isDirection = direction;
    }

    public boolean getIsDirection(){
        return this.isDirection;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public long getSize() {
        return size;
    }
}
