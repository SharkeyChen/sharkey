package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Utils.MyLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/MD")
public class MarkDController {
    @Value("${sharkey.static.img.path}")
    private String StaticImgPath;

    @RequestMapping("/uploadimg")
    public RespBean UploadImg(@RequestParam("file")MultipartFile file, @RequestParam("type")String type){
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        String savePath =  StaticImgPath + "/" + type + "img/";
        MyLogger.logger(savePath);
        File newFile = new File(savePath, filename);
        if(!newFile.exists()){
            MyLogger.logger("文件不存在哦");
            try{
                newFile.createNewFile();
            }catch (Exception e){
                MyLogger.logger(e.getMessage());
            }
        }
        try{
            file.transferTo(newFile);
            return RespBean.ok("文件上传成功", "/" + type + "img/" + filename);
        }catch (Exception e){
            MyLogger.logger(e.getMessage());
            return RespBean.error("文件上传失败");
        }
    }
}
