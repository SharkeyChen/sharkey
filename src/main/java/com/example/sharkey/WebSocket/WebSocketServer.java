package com.example.sharkey.WebSocket;

import com.example.sharkey.Utils.GameRoom;
import com.example.sharkey.Utils.RoomGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws")
@Component
public class WebSocketServer {

    @PostConstruct
    public void init() {
        System.out.println("websocket 加载");
    }
    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();
//    private static Map<String, GameRoom> rMap = new Hashtable<>();
    private static RoomGroup rGroup = new RoomGroup();
    private static Map<String, Session> UidtoSession = new Hashtable<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        SessionSet.add(session);
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        log.info("当前SessionSet数量为：{}",SessionSet.size());
        SendMessage(session, "连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        for(Map.Entry<String,Session> entry : UidtoSession.entrySet()){
            if(session.equals(entry.getValue())){
                UidtoSession.remove(entry.getKey());
                break;
            }
        }
        SessionSet.remove(session);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);

    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
//        log.info("来自客户端的消息：{}，session为：{}",message,session.getId());
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> smap = mapper.readValue(message, HashMap.class);
        Object code = smap.get("code");
        if(code.toString().equals("101")){
            Object id = smap.get("id");
            if(UidtoSession.containsKey(id.toString())){
                UidtoSession.replace(id.toString(), session);
            }
            else {
                UidtoSession.put(id.toString(), session);
            }
        }
        else{
            rGroup.Distribute(message, session);
        }
    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) {
        try {
//            log.info("正要发送的Session:{}", session.getBasicRemote().toString());
            session.getBasicRemote().sendText(String.format("%s",message));

        } catch (IOException e) {
//            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }



    public static void InviteToPlay(String message, String username){
        if(UidtoSession.containsKey(username)){
            SendMessage(UidtoSession.get(username), message);
        }
        else{
            System.out.println("邀请失败");
            log.warn("该用户还未上线:{}", username);
        }
    }


    /*
     *创建房间并插入
     */
    public static void CreateRoom(String uuid,GameRoom room){
        rGroup.CreateRoom(uuid, room);
    }

    /*****获取在线的用户列表*****/
    public static ArrayList<String> getUsersList(){
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String, Session> entry : UidtoSession.entrySet()){
            list.add(entry.getKey());
        }
        return list;
    }
}