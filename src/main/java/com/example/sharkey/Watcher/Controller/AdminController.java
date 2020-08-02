package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Entity.User;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/getmenu")
    public RespPageBean getMenuTree(){
        return adminService.getMenuTree();
    }

    @GetMapping("/getuser")
    public RespPageBean getUsers(){
        return adminService.getUsers();
    }

    @DeleteMapping("/deleteuser")
    public RespBean deleteUsers(@RequestBody List<User> list){
        return adminService.deleteUsers(list);
    }

    @GetMapping("/getipfilter")
    public RespPageBean getIpFilters(){
        MyLogger.logger("yes");
        return adminService.getIpFilters();
    }
}
