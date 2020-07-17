package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Model.RespBean;
import com.example.sharkey.Model.RespPageBean;
import com.example.sharkey.Model.User;
import com.example.sharkey.Watcher.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public User getUserByPrimaryAccount(String username){
        return userMapper.getUserByPrimaryAccount(username);
    }

    public boolean changeProfile(String profile, String username){
        System.out.println(profile + " " + username);
        return userMapper.changeProfile(profile, username);
    }

    public RespBean insertUser(User user){
        boolean result = userMapper.insertUser(user);
        if(result == false){
            return RespBean.error("注册失败(可能是用户名已被使用)");
        }
        else{
            return RespBean.ok("注册成功");
        }
    }

    public RespPageBean getAllUsers(){
        List<User> data = userMapper.getAllUsers();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespPageBean getUsersByListParams(List<String> list){
//        if(list == null || list.size() == 0){
//
//        }
        List<User> data = userMapper.getUsersByListParams(list);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespBean UpdateUserInfo(User user){
        if(userMapper.UpdateUserInfo(user)){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
