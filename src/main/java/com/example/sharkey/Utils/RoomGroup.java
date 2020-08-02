package com.example.sharkey.Utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.Session;
import java.util.*;

public class RoomGroup {

    /**房间号，房间列表**/
    private static Map<String, GameRoom> RidMap = new Hashtable<>();

    /******用户名，Session列表(存放所有在游戏中的用户的Session******/
    private static Map<String, Session> UidToSession = new Hashtable<>();

    public RoomGroup(){

    }

    /********处理器*******/
    public void Distribute(String message, Session session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> smap = mapper.readValue(message, HashMap.class);
        String code = smap.get("code").toString();
        /*****玩家加入房间****/
        if(code.equals("1000")){
            String id = smap.get("id").toString();
            String rid = smap.get("rid").toString();
            if(JoinRoom(rid, id, session)){
                MyLogger.logger("加入房间成功");
            }
        }
        /*****玩家准备****/
        else if(code.equals("1002")){
            String id = smap.get("id").toString();
            String rid = smap.get("rid").toString();
            if(PlayerReady(rid, id, message)){
                MyLogger.logger(id + "玩家已经准备");
            }
        }
        /******玩家游戏内容*****/
        else if(code.equals("1004") ||code.equals("1005")){
            String rid = smap.get("rid").toString();
            if(!TransContent(rid, message)){
                MyLogger.logger("转发出现问题");
            }
        }
        /*********比赛结束*******/
        else if(code.equals("1006")){
            String rid = smap.get("rid").toString();
            if(GameOver(rid)){
                MyLogger.logger("比赛结束");
            }
        }
        /******玩家退出******/
        else if(code.equals("1050")){
            String rid = smap.get("rid").toString();
            String id = smap.get("id").toString();
            if(PlayerExist(rid, id)){
                MyLogger.logger("玩家退出成功");
            }
            if(isRoomExist(rid)){
                if(!TransContent(rid, message)){
                    MyLogger.logger("转发消息出现问题");
                }
            }
        }
    }

    /****创建房间***/
    public boolean CreateRoom(String uuid, GameRoom room){
        if(RidMap.put(uuid, room) == null) {
            MyLogger.logger("房间创建成功,uuid:" + uuid);
            return true;
        }
        MyLogger.logger("房间创建失败, uuid:" +uuid);
        return false;
    }

    /******房间是否存在*****/
    public boolean isRoomExist(String roomId){
        if(!RidMap.containsKey(roomId)){
            MyLogger.logger("房间不存在");
            return false;
        }
        return true;
    }

    /******加入房间******/
    public boolean JoinRoom(String roomId, String playerId, Session session){
        if(!isRoomExist(roomId)){
            return false;
        }
        GameRoom room = RidMap.get(roomId);
        if(room.AddPlayer(playerId)){
            UidToSession.put(playerId, session);
            TellRoom(1001, roomId);
            return true;
        }
        return false;
    }

    /********玩家准备******/
    public boolean PlayerReady(String roomId, String playerId, String message){
        if(!isRoomExist(roomId)){
            return false;
        }
        GameRoom room = RidMap.get(roomId);
        if(room.setPlayerReady(playerId, Player.PLAYER_READY)){
            if(!TransContent(roomId, message)){
                return false;
            }
            if(room.isAllPlayerReady()){
                if(TellRoom(1003, roomId)){
                    MyLogger.logger("游戏开始");
                    room.setAllPlayerState(Player.PLAYER_PLAYING);
                }
            }
            return true;
        }
        return false;
    }

    /*****玩家在房间做的事，服务器主动告诉房间中的人*****/
    public boolean TellRoom(int code, String roomId){
        if(!isRoomExist(roomId)){
            return false;
        }
        GameRoom room = RidMap.get(roomId);
        List<String> nameList = room.getPlayerList();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("players", nameList);
        if(room.getGameType().equals("你画我猜") && code == 1003){
            String first = nameList.get((int)(Math.random() * room.getPlayerNum()));
            jsonObject.put("painter", first);
            jsonObject.put("keyword", "林克");
        }
        SendMessageByGroup(nameList, jsonObject.toJSONString());
        return true;
    }

    /*******游戏内容转发*****/
    public boolean TransContent(String roomId, String message){
        if(!isRoomExist(roomId)){
            return false;
        }
        GameRoom room = RidMap.get(roomId);
        List<String> nameList = room.getPlayerList();
        SendMessageByGroup(nameList, message);
        return true;
    }


    /******玩家退出******/
    public boolean PlayerExist(String roomId, String playerId){
        if(!isRoomExist(roomId)){
            return false;
        }
        GameRoom room = RidMap.get(roomId);
        if(!room.KickPlayer(playerId)){
            return false;
        }
        if(room.getPlayerNum() == 0){
            RidMap.remove(roomId);
        }
        return true;
    }

    /******比赛结束******/
    public boolean GameOver(String roomId){
        if(!isRoomExist(roomId)){
            return false;
        }
        GameRoom room = RidMap.get(roomId);
        room.setAllPlayerState(Player.PLAYER_NOT_READY);
        return true;
    }


    /*****根据名字获取Session*****/
    public List<Session> getSessionsByName(List<String> nameList){
        List<Session> sList = new ArrayList<Session>();
        for(String s : nameList){
            sList.add(UidToSession.get(s));
        }
        return sList;
    }

    /******组内群发*****/
    public void SendMessageByGroup(List<String> nameList, String message){
        List<Session> list = getSessionsByName(nameList);
        for(Session session : list){
            try{
                MyLogger.logger(String.format("正要向%s发送消息，消息为%s",session.getId(), message));
                session.getBasicRemote().sendText(message);
            }catch (Exception e){
                MyLogger.logger(String.format("发送消息失败，BUG为：", e.getMessage()));
            }
        }
    }
}
