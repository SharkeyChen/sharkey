package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.IpFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IpFilterMapper {
    int getFilter(@Param("ip")String ip);
}
