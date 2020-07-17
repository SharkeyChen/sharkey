package com.example.sharkey.Watcher.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.sharkey.Model.User;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Utils.TokenUtils;
import com.example.sharkey.Watcher.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sharkey.Model.RespBean;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @CrossOrigin("*")
    @PostMapping("/login")
    public RespBean Login(@RequestParam(value="username") String username, @RequestParam(value="password") String password){
        User user = userService.getUserByPrimaryAccount(username);
        if(user != null){
            if(password.equals(user.getPassword())){
                JSONObject jb = new JSONObject();
                String token = TokenUtils.getToken(username, password);
                jb.put("token", token);
                user.EncodePass();
                jb.put("user", user);
                return RespBean.ok("成功登陆!", jb);
            }
            else{
                return RespBean.error("密码或账号错误");
            }
        }
        else{
            return RespBean.error("账号不存在");
        }
    }
}
