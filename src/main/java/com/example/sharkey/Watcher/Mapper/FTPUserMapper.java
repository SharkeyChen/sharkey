package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.FTPUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FTPUserMapper {
    boolean insertFTPUser(FTPUser user);

    FTPUser selectFTPUser(String userid);
}
