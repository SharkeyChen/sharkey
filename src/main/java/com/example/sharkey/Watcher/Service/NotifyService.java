package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.Notify;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Watcher.Mapper.NotifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyService {

    @Autowired
    private NotifyMapper notifyMapper;

    public RespPageBean getNotificationsByUId(int uid){
        RespPageBean data = new RespPageBean();
        try{
            List<Notify> list = notifyMapper.getNotificationsByUsername(uid);
            data.setData(list);
            data.setTotal(Long.parseLong(String.valueOf(list.size())));
            return data;
        }catch (Exception e){
            e.printStackTrace();
            return data;
        }
    }
}
