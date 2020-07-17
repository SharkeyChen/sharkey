package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Model.Hello;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HelloMapper {
    public Hello getHello();
}
