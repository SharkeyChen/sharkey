package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.UserSocial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSocialMapper {
    boolean Insert(List<UserSocial> list);

    boolean Update(List<UserSocial> list);

    boolean Delete(List<UserSocial> list);
}
