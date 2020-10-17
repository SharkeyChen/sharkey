package com.example.sharkey.Entity;

import java.sql.Timestamp;
import java.util.List;

public class Notify {
    private int id;

    private int toid;

    private int authorid;

    private String message;

    private Timestamp time;

    private User user;

    private List<User> toUser;

    public List<User> getToUser() {
        return toUser;
    }

    public void setToUser(List<User> toUser) {
        this.toUser = toUser;
    }

    public int getAuthorid() {
        return authorid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public int getId() {
        return id;
    }

    public int getToid() {
        return toid;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
