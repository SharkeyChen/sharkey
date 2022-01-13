package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    User getUserByPrimaryAccount(@Param("username")String username);

    boolean changeProfile(@Param("profile")String profile, @Param("username")String username);

    List<User> getAllUsers();

    int getUserNum(@Param("username")String username);

    int getEmailNum(@Param("email")String email);

    boolean insertUser(User user);

    List<User> getUsersByListParams(List<String> list);

    boolean UpdateUserInfo(User user);

    boolean UpdateUserIp(int id, String ip);

    List<ClockIn> getClockInList(@Param("username")String username);

    boolean insertClockIn(ClockIn ci);

    List<Memo> getMemoList(@Param("username")String username);

    boolean insertMemo(Memo memo);

    boolean deleteMemo(Memo memo);

    List<Memo> getMemoToday();

    DIYPage getDIYPageByUsername(@Param("username") String username);

    boolean insertDIYPage(DIYPage diypage);

    boolean deleteDIYPage(@Param("username") String username);

    UserConfig getUserConfigByUserName(@Param("username") String username);

    boolean updateUserConfig(UserConfig userConfig);

    VoteConfig getVoteConfig(@Param("username")String username);

    boolean updateVoteConfig(VoteConfig voteConfig);

    boolean updateCheckEmailByUsername(@Param("username")String username);


}
