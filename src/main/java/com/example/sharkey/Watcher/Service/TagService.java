package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Model.RespPageBean;
import com.example.sharkey.Model.Tag;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    public RespPageBean getTagListByTitle(String title){
        MyLogger.logger(title);
        RespPageBean data = new RespPageBean();
        List<Tag> list = tagMapper.getTagListByTitle(title);
        data.setData(list);
        data.setTotal(Long.parseLong(String.valueOf(list.size())));
        return data;
    }
}
