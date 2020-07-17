package com.example.sharkey.Utils;

public class Player extends Object {
    private String username;

    private int playerState;

    public static final int PLAYER_READY = 1;

    public static final int PLAYER_PLAYING = 2;

    public static final int PLAYER_NOT_READY = 0;

    public Player(){
        this.username = null;
        this.playerState = this.PLAYER_NOT_READY;
    }

    public Player(String username){
        this.username = username;
        this.playerState = this.PLAYER_NOT_READY;
    }

    public void setPlayerState(int state){
        this.playerState = state;
    }

    public String getUsername(){return this.username;}

    public boolean isReady(){
        if(this.playerState == this.PLAYER_READY){
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return "playername:" + this.username + " and playerState:" + this.playerState;
    }
}
