package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Watcher.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PutMapping("/getlist")
    public RespPageBean getTagListByTitle(@RequestParam("title")String title){
        return tagService.getTagListByTitle(title);
    }
}
