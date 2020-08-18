package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.*;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${sharkey.static.img.path}")
    private String StaticImgPath;

    @GetMapping("/")
    User getUserInfoByUsername(@RequestParam("username") String username){
        User user = userService.getUserByPrimaryAccount(username);
        user.EncodePass();
        return user;
    }

    @PostMapping("/getUsers")
    RespPageBean getUserInfoByNameList(@RequestBody List<String> list){
        MyLogger.logger(list.toString());
        return userService.getUsersByListParams(list);
    }

    @GetMapping("/all")
    RespPageBean getAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping("/profile")
    RespBean uploadProfile(@RequestParam("file")MultipartFile file, @RequestParam("user")String username, @RequestParam("profile")String profile){
        String filename = "";
        String savePath =  StaticImgPath + "/profile/";
        if(profile == null){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
            filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        }
        else{
            filename = profile.substring(9);
        }
        MyLogger.logger("########" + savePath + "##########" + filename + "############");
        File newFile = new File(savePath, filename);
        MyLogger.logger(newFile.getAbsolutePath());
        if(!newFile.exists()){
            System.out.println("not exist");
            try{
                newFile.createNewFile();
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Create New File Failed!!!");
            }
        }
        try{
            file.transferTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("文件保存异常");
        }
        return RespBean.ok("上传成功");
    }

    @PutMapping("/updateInfo")
    RespBean updateUserInfo(@RequestBody User user){
        return userService.UpdateUserInfo(user);
    }

    @GetMapping("/clockin")
    RespPageBean getClockInList(@RequestParam("username")String username){
        return userService.getClockListByUserName(username);
    }

    @PutMapping("/clockin")
    RespBean insertClock(@RequestBody ClockIn ci){
        return userService.insertClockIn(ci);
    }

    @GetMapping("/memo")
    RespPageBean getMemoList(@RequestParam("username")String username){
        return userService.getMemoListByUserName(username);
    }

    @PutMapping("/memo")
    RespBean insertMemo(@RequestBody Memo memo){
        return userService.insertMemo(memo);
    }

    @DeleteMapping("/memo")
    RespBean deleteMemo(@RequestBody Memo memo){
        MyLogger.logger(memo.getMemo());
        return userService.deleteMemo(memo);
    }
}
