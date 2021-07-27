package com.example.sharkey.Utils;

import com.example.sharkey.Entity.MyFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static MyFile turnFile(File file){
        MyFile mf = new MyFile();
        if(!file.exists()){
            return null;
        }
        mf.setName(file.getName());
        mf.setSize(file.length());
        mf.setTime(file.lastModified());
        mf.setDirection(file.isDirectory());
        return mf;
    }

    public static List<MyFile> turnFileList(File[] list){
        List<MyFile> fList = new ArrayList<>();
        for(File file : list){
            MyFile t = turnFile(file);
            if(t != null){
                fList.add(t);
            }
        }
        return fList;
    }

    public static boolean deleteDir(File file){
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
}
