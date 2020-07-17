package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Watcher.Service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

//    @GetMapping("/hello")
//    public Hello HelloWorld(){
//        return helloService.getHello();
//    }

    @GetMapping("/hello")
    public String Hlwd(){
        return "Hellwo world";
    }
}
