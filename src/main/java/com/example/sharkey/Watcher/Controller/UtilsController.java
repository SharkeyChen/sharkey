package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Entity.UserSocial;
import com.example.sharkey.Watcher.Service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utils")
public class UtilsController {
    @Autowired
    private UtilsService utilsService;

    @GetMapping("/social")
    public RespPageBean getSocialList(){
        return utilsService.getSocialList();
    }

    @PutMapping("/addusersocial")
    public RespBean addUserSocial(@RequestBody UserSocial userSocial){
        return utilsService.addUserSocial(userSocial);
    }

    @GetMapping("/getmysocial")
    public RespPageBean getSocialByUid(@RequestParam("uid") int uid){
        return utilsService.getSocialByUid(uid);
    }

    @DeleteMapping("/deletesocial")
    public RespBean deleteSocialById(@RequestParam("id") int id){
        return utilsService.deleteSocialById(id);
    }
}
