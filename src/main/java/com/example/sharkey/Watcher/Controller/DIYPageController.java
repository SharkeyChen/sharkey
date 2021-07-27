package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.DIYPage;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Service.FileService;
import com.example.sharkey.Watcher.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/diy")
public class DIYPageController {
    @Value("${sharkey.static.img.path}")
    private String basePath;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/delete")
    public RespBean deleteDIYPage(@RequestParam("username") String username) {
        return userService.deleteDIYPage(username);
    }

    @PutMapping("/insert")
    public RespBean insertDIYPage(@RequestBody DIYPage diyPage) {
        return userService.insertDIYPage(diyPage);
    }

    @PostMapping("/upload")
    public RespBean uploadDIYFiles(@RequestParam("files") MultipartFile files, @RequestParam("paths") String paths, @RequestParam(value = "username") String username) {
        RespBean bean;
        if (paths.endsWith("index.html")) {
            bean = userService.insertDIYPage(username, paths);
            if (bean.getCode() == 500) {
                return bean;
            }
        }
        bean = fileService.saveFiles(files, paths, basePath + "/DIYPage/" + username);
        if (bean.getCode() == 500) {
            return bean;
        }
        return RespBean.ok(29200, "上传成功");
    }
}
