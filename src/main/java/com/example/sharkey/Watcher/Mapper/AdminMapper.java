package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.IpFilter;
import com.example.sharkey.Entity.Menu;
import com.example.sharkey.Entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<Menu> getMenuTree();

    List<User> getUsers();

    boolean deleteUserSocialList(List<User> list);

    boolean deleteUserList(List<User> list);

    List<IpFilter> getIpFilters();
}
