package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.Bug;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Watcher.Mapper.BugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BugService {
    @Autowired
    private BugMapper bugMapper;

    public RespBean addBug(Bug bug){
        bug.setCreateTime();
        if(bugMapper.addBug(bug)){
            return RespBean.ok("添加成功");
        }
        else{
            return RespBean.error("添加失败");
        }
    }

    public RespPageBean getBugList(){
        List<Bug> list = bugMapper.getAllBugList();
        RespPageBean bean = new RespPageBean();
        bean.setData(list);
        bean.setTotal(Long.parseLong(String.valueOf(list.size())));
        return bean;
    }
}
