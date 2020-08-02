package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Entity.Social;
import com.example.sharkey.Entity.UserSocial;
import com.example.sharkey.Watcher.Mapper.UtilsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilsService {
    @Autowired
    private UtilsMapper utilsMapper;

    public RespPageBean getSocialList(){
        List<Social> data = utilsMapper.getSocialList();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespBean addUserSocial(UserSocial userSocial){
        if(utilsMapper.addUserSocial(userSocial)){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    public RespPageBean getSocialByUid(int uid){
        List<UserSocial> data = utilsMapper.getSocialByUid(uid);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(Long.parseLong(String.valueOf(data.size())));
        return bean;
    }

    public RespBean deleteSocialById(int id){
        if(utilsMapper.deleteSocialById(id)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
