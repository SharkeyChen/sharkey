package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.Social;
import com.example.sharkey.Entity.UserSocial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UtilsMapper {
    List<Social> getSocialList();

    boolean addUserSocial(UserSocial userSocial);

    List<UserSocial> getSocialByUid(int uid);

    boolean deleteSocialById(int id);
}
