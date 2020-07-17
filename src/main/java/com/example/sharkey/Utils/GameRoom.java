package com.example.sharkey.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameRoom extends Object {
    private String GameType;

    private String uid;

    private int PlayerNum;

    private int MaxPlayer;

    private Map<String, Player> PlayerMap;

    /**新建房间**/
    public GameRoom(String GameType, String uid, int maxPlayer){
        this.GameType = GameType;
        this.uid = uid;
        this.PlayerNum = 0;
        this.MaxPlayer = maxPlayer;
        this.PlayerMap = new HashMap<String, Player>();
    }

    public String getGameType(){return this.GameType;}

    /**加入玩家**/
    public boolean AddPlayer(String uid){
        if(PlayerNum == MaxPlayer){
            MyLogger.logger("房间已满，无法加入玩家");
            return false;
        }
        if(PlayerMap.containsKey(uid) == true){
            MyLogger.logger("用户已存在，无法加入房间");
            return false;
        }
        else{
            Player newOne = new Player(uid);
            PlayerMap.put(uid, newOne);
            PlayerNum += 1;
            MyLogger.logger("用户加入房间成功");
            return true;
        }
    }

    /**退出玩家*/
    public boolean ExitPlayer(String uid){
        if(PlayerMap.containsKey(uid)){
            PlayerMap.remove(uid);
            PlayerNum -= 1;
            MyLogger.logger(uid + "退出");
            return true;
        }
        else{
            MyLogger.logger(uid + "用户不存在,无法退出");
            return false;
        }
    }

    /**修改玩家状态**/
    public boolean ChangePlayerState(String uid, int state){
        if(PlayerMap.containsKey(uid)){
            PlayerMap.get(uid).setPlayerState(state);
            MyLogger.logger("玩家状态修改完成");
            MyLogger.logger(PlayerMap.get(uid).toString());
            return true;
        }
        MyLogger.logger("修改状态失败");
        return false;
    }

    /****查看房间是否满房***/
    public boolean isFullRoom(){
        if(PlayerNum == MaxPlayer){
            return true;
        }
        return false;
    }

    /***返回玩家id列表***/
    public ArrayList<String> getPlayerList(){
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String,Player> entry : PlayerMap.entrySet()){
            list.add(entry.getValue().getUsername());
        }
        return list;
    }

    /******返回房间中玩家个数*****/
    public int getPlayerNum(){
        return this.PlayerNum;
    }

    /*****房间是否存在某个人*****/
    public boolean isExistPlayer(String name){
        if(PlayerMap.containsKey(name)){
            return true;
        }
        else{
            MyLogger.logger(name + "不在房间之中");
            return false;
        }
    }

    /**********剔除某个玩家********/
    public boolean KickPlayer(String name){
        if(this.isExistPlayer(name)){
            this.PlayerMap.remove(name);
            this.PlayerNum --;
            MyLogger.logger("玩家" + name + "离开房间");
            return true;
        }
        return false;
    }

    /*****改变某个人的当前状态******/
    public boolean setPlayerReady(String name, int state){
        if(isExistPlayer(name)){
            MyLogger.logger(name + "状态改变成功");
            PlayerMap.get(name).setPlayerState(state);
            return true;
        };
        return false;
    }

    public void setAllPlayerState(int state){
        for(Map.Entry<String, Player> entry :PlayerMap.entrySet()){
            entry.getValue().setPlayerState(state);
        }
    }

    /*************检查房间中的玩家的准备状态************/
    public boolean isAllPlayerReady(){
        for(Map.Entry<String, Player> entry : PlayerMap.entrySet()){
            if(!entry.getValue().isReady()){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        String buf = String.format("uid:%s\nType:%s\nMaxPlayer:%d\nNowPlayer:%d\n",this.uid, this.GameType, this.MaxPlayer, this.PlayerNum);
        for(Map.Entry<String,Player> entry : PlayerMap.entrySet()){
            buf += entry.getValue().toString();
            buf += "\n";
        }
        return buf;
    }
}
