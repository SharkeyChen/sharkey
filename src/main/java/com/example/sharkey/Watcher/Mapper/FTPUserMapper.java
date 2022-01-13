package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.FTPUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FTPUserMapper {
    boolean insertFTPUser(FTPUser user);

    FTPUser selectFTPUser(@Param("userid")String userid);
}
