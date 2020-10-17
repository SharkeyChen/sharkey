package com.example.sharkey.Entity;

public class UserConfig {
    private int id;

    private int uid;

    private boolean mailnotify;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public boolean getMailnotify(){return this.mailnotify;}

    public void setMailnotify(boolean mailnotify) {
        this.mailnotify = mailnotify;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
