package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.Bug;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Watcher.Service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bug")
public class BugController {
    @Autowired
    private BugService bugService;

    @GetMapping("/all")
    public RespPageBean getBugList(){
        return bugService.getBugList();
    }

    @PostMapping("/add")
    public RespBean addBug(@RequestBody Bug bug){
        return bugService.addBug(bug);
    }
}
