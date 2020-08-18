package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.ClockIn;
import com.example.sharkey.Entity.Memo;
import com.example.sharkey.Entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserByPrimaryAccount(String username);

    boolean changeProfile(String profile, String username);

    List<User> getAllUsers();

    boolean insertUser(User user);

    List<User> getUsersByListParams(List<String> list);

    boolean UpdateUserInfo(User user);

    boolean UpdateUserIp(int id, String ip);

    List<ClockIn> getClockInList(String username);

    boolean insertClockIn(ClockIn ci);

    List<Memo> getMemoList(String username);

    boolean insertMemo(Memo memo);

    boolean deleteMemo(Memo memo);
}
