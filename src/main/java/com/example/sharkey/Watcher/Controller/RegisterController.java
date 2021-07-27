package com.example.sharkey.Watcher.Controller;


import com.example.sharkey.Annotation.Remind;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.User;
import com.example.sharkey.Watcher.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @Remind
    @PostMapping("/register")
    public RespBean RegisterUser(@RequestBody User user){
        return userService.insertUser(user);
    }
}
