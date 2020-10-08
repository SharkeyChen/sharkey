package com.example.sharkey.Watcher.Controller;


import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Watcher.Service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotifyController {
    @Autowired
    private NotifyService notifyService;

    @GetMapping("/getall")
    public RespPageBean getNotificationsByUId(@RequestParam("uid")int uid){
        return notifyService.getNotificationsByUId(uid);
    }
}
