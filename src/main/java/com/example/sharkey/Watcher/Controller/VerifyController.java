package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Watcher.Service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerifyController {
    @Autowired
    private VerifyService verifyService;

    @GetMapping("/email")
    public RespBean verifyEmail(@RequestParam("token")String token, @RequestParam("username")String username){
        return verifyService.verifyToken(token, username);
    }

    @GetMapping("/send")
    public RespBean sendVerify(@RequestParam("email")String email, @RequestParam("username")String username){
        return verifyService.sendVerifyEmail(username, email);
    }
}
