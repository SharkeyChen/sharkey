package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.MyFile;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Utils.FileUtils;
import com.example.sharkey.Utils.MyLogger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    public RespBean saveFiles(MultipartFile files, String paths, String prefix) {
        if (files == null || paths == null) {
            return RespBean.error("未知错误");
        }
        try {
            MultipartFile nfile = files;
            String path = paths;
            File file = new File(prefix + path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            nfile.transferTo(file);
            return RespBean.ok("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("出现未知错误");
        }
    }

    public boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (String path : children) {
                boolean res = deleteDir(new File(file, path));
                if (!res) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public List<MyFile> getFileList(File file){
        List<MyFile> list = new ArrayList<>();
        if(file == null){
            return list;
        }
        if(!file.isDirectory()){
            return list;
        }
        return FileUtils.turnFileList(file.listFiles());
    }
}
