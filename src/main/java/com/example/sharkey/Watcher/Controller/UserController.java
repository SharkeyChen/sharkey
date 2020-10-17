package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.*;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Service.UserService;
import org.apache.commons.lang.StringUtils;
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
        String realProfile = profile;
        if(StringUtils.isBlank(profile)){
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
            filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
            realProfile = "/profile/" + filename;
            try{
                userService.changeProfile("/profile/" + filename, username);
            }catch (Exception e){
                return RespBean.error("未知错误");
            }
        }
        else{
            filename = profile.substring(9);
        }
        File newFile = new File(savePath, filename);
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
        return RespBean.ok("上传成功", realProfile);
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

    @GetMapping("/diypage")
    RespBean getDIYPage(@RequestParam("username")String username){
        return userService.getDIYPageByUsername(username);
    }

    @GetMapping("/userconfig")
    RespBean getUserConfig(@RequestParam("username")String username){
        return userService.getUserConfigByUserName(username);
    }

    @PutMapping("/userconfig")
    RespBean updateUserConfig(@RequestBody UserConfig userConfig){
        return userService.updateUserConfig(userConfig);
    }

    @GetMapping("/voteconfig")
    RespBean getVoteConfig(@RequestParam("username")String username){
        return userService.getVoteConfigByUsername(username);
    }

    @PutMapping("/voteconfig")
    RespBean updateVoteConfig(@RequestBody VoteConfig voteConfig){
        return userService.updateVoteConfig(voteConfig);
    }
}
