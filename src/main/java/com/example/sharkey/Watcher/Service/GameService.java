package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Model.Game;
import com.example.sharkey.Model.RespPageBean;
import com.example.sharkey.Watcher.Mapper.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameMapper gameMapper;

    public RespPageBean getGameList(){
        RespPageBean bean = new RespPageBean();
        List<Game> list = gameMapper.getGameList();
        bean.setData(list);
        bean.setTotal(Long.parseLong(String.valueOf(list.size())));
        return bean;
    }
}
