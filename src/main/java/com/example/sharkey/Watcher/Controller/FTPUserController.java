package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Annotation.IgnoreVerify;
import com.example.sharkey.Entity.FTPUser;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Service.FTPUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/ftpuser")
public class FTPUserController {
    @Autowired
    private FTPUserService ftpUserService;

    @Value("${sharkey.static.pan.dir}")
    private String panDir;

    @PostMapping("/insert")
    public RespBean addFTPUser(@RequestBody FTPUser ftpUser){
        String homeDir = panDir + "/" + ftpUser.getUserid();
        MyLogger.logger(homeDir);
        File file=new File(homeDir);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        ftpUser.setHomedirectory(homeDir);
        return ftpUserService.addFTPUser(ftpUser);
    }

    @GetMapping("/get")
    public RespBean getFTPUser(@RequestParam("username")String userid){
        return ftpUserService.getFTPUser(userid);
    }

    @GetMapping("/current")
    public RespPageBean getCurrentPath(@RequestParam("username")String userid){
        return ftpUserService.getCurrentPath(userid);
    }

    @GetMapping("/list")
    public RespPageBean getFileList(@RequestParam("username")String username){
        return ftpUserService.getFileList(username);
    }

    @GetMapping("/cd")
    public RespBean changeDir(@RequestParam("type")int type, @RequestParam("username")String username,
                              @RequestParam(value = "path", defaultValue = "")String path, @RequestParam("folder")String folder, @RequestParam(required = false, value = "step", defaultValue = "1")int step){
        if(path == null){
            path = "";
        }
        return ftpUserService.changeDir(type, username, path, folder, step);
    }

    @PostMapping("/upload")
    public RespBean uploadFile(@RequestParam("username")String username, @RequestParam("files")MultipartFile files,
                               @RequestParam("paths")String paths, @RequestParam("path")String path){
        MyLogger.logger(path);
        return ftpUserService.uploadFile(username, files, paths, path);
    }

    @DeleteMapping("/delete")
    public RespBean deleteFile(@RequestParam("username")String username, @RequestBody String[] list, @RequestParam("path")String path){
        if(path == null){
            path = "";
        }
        return ftpUserService.deleteFile(username, list, path);
    }

    @PutMapping("/create")
    public RespBean addFolder(@RequestParam("username")String username, @RequestParam("path")String path, @RequestParam("folder")String folder){
        if(path == null){
            path = "";
        }
        return ftpUserService.createFolder(username, path, folder);
    }

    @IgnoreVerify
    @GetMapping("/download")
    public RespBean downloadFile(@RequestParam("username")String username, @RequestParam("path")String path,
                                 @RequestParam("filename")String filename, HttpServletResponse response){
        if(path == null){
            path = "";
        }
        return ftpUserService.downloadFile(username, path, filename, response);
    }
}
