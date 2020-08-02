package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.IpFilter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IpFilterMapper {
    int getFilter(String ip);
}
