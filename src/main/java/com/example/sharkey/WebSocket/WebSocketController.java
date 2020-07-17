package com.example.sharkey.WebSocket;

import com.alibaba.fastjson.JSONObject;
import com.example.sharkey.Model.Game;
import com.example.sharkey.Model.RespBean;
import com.example.sharkey.Model.RespPageBean;
import com.example.sharkey.Utils.GameRoom;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/socket")
public class WebSocketController {
    @Autowired
    private UserService userService;

    /******创建房间**********/
    @PostMapping("/create")
    public RespBean CreateRoom(@RequestParam("username") String username, @RequestBody Game game){
        String uuid = UUID.randomUUID().toString();
        GameRoom room = new GameRoom(game.getName(), uuid, game.getMaxPlayer());
        WebSocketServer.CreateRoom(uuid, room);
        JSONObject json = new JSONObject();
        json.put("code", 102);
        json.put("rid", uuid);
        json.put("game", game);
        WebSocketServer.InviteToPlay(json.toJSONString(), username);
        return RespBean.ok(uuid);
    }

    @GetMapping("/getOnlinePlayers")
    public RespPageBean getOnlinePlayers(){
        ArrayList<String> list = WebSocketServer.getUsersList();
        MyLogger.logger(list.toString());
        return  userService.getUsersByListParams(list);
    }
}
