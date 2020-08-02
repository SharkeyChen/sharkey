package com.example.sharkey.Watcher.Service;

/**
 * Author: Sharkey
 * Date 2020/8/1
 */


import com.example.sharkey.Entity.*;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public RespPageBean getMenuTree(){
        List<Menu> data = adminMapper.getMenuTree();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespPageBean getUsers(){
        List<User> data = adminMapper.getUsers();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespBean deleteUsers(List<User> list){
        if(adminMapper.deleteUserList(list) && adminMapper.deleteUserSocialList(list)){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    public RespPageBean getIpFilters(){
        List<IpFilter> data = adminMapper.getIpFilters();
        MyLogger.logger("data");
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }
}
