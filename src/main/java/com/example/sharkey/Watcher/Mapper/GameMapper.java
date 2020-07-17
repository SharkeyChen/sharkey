package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Model.Game;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameMapper {
    public List<Game> getGameList();
}
