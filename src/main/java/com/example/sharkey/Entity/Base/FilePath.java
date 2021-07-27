package com.example.sharkey.Entity.Base;

import java.util.ArrayList;
import java.util.List;

public class FilePath {
    private List<String> pathList;

    public FilePath(){
        pathList = new ArrayList<>();
    }


    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    public void addPath(String path){
        pathList.add(path);
    }

    public int getCount(){
        return pathList.size();
    }

    public boolean deleteNumSuffix(int num){
        if(num > pathList.size()){
            return false;
        }
        for(int i = 0;i < num;i ++){
            pathList.remove(pathList.size() - 1);
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuffer res = new StringBuffer();
        for(String now : pathList){
            res.append("/").append(now);
        }
        return res.toString();
    }
}
