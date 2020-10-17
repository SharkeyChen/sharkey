package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Utils.MyLogger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileService {
    public RespBean saveFiles(MultipartFile[] files,String[] paths ,String prefix){
        if(files.length != paths.length){
            return RespBean.error("未知错误");
        }
        try{
            for(int i = 0;i < files.length;i ++){
                MultipartFile nfile = files[i];
                String path = paths[i];
                File file = new File(prefix + path);
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                nfile.transferTo(file);
            }
            return RespBean.ok("上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("出现未知错误");
        }
    }

    public boolean deleteDir(File file){
        MyLogger.logger(file.getPath());
        if(file.isDirectory()){
            String[] children = file.list();
            for(String path : children){
                boolean res = deleteDir(new File(file, path));
                if(!res){
                    return false;
                }
            }
        }
        return file.delete();
    }
}
