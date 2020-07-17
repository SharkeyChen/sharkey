package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Watcher.Mapper.HelloMapper;
import com.example.sharkey.Model.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Autowired
    HelloMapper helloMapper;

    public Hello getHello(){
        return helloMapper.getHello();
    }
}
