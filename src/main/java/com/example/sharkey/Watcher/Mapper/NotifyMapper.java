package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.Notify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotifyMapper {
    List<Notify> getNotificationsByUsername(int uid);
}
