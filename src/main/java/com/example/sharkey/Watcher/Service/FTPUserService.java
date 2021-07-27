package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.Base.FilePath;
import com.example.sharkey.Entity.FTPUser;
import com.example.sharkey.Entity.MyFile;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Entity.RespPageBean;
import com.example.sharkey.Utils.FileUtils;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.FTPUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FTPUserService {

    @Value("${sharkey.static.img.path}")
    private String baseDir;

    @Autowired
    private FTPUserMapper ftpUserMapper;

    @Autowired
    private FileService fileService;

    private Map<String, String> basePath = new HashMap<>();

    private Map<String, FilePath> currentPath = new HashMap<>();

    public RespBean addFTPUser(FTPUser ftpUser){
        try{
            File dir = new File(baseDir + ftpUser.getHomedirectory());
            if(!dir.exists()){
                dir.mkdirs();
            }
            if(ftpUserMapper.insertFTPUser(ftpUser)){
                return RespBean.ok("插入成功");
            }else{
                return RespBean.error("插入失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("插入失败");
        }
    }

    public RespBean getFTPUser(String userid){
        try{
            FTPUser ftpUser = ftpUserMapper.selectFTPUser(userid);
            if(ftpUser == null){
                return RespBean.ok(29200, "no");
            }
            else{
                return RespBean.ok(29200, "yes");
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("未知错误");
        }
    }

    public RespPageBean getCurrentPath(String userid){
        RespPageBean bean = new RespPageBean();
        List<String> list;
        if(!currentPath.containsKey(userid)){
            currentPath.put(userid, new FilePath());
        }
        list = currentPath.get(userid).getPathList();
        bean.setData(list);
        bean.setTotal(Long.parseLong(String.valueOf(list.size())));
        return bean;
    }

    public RespPageBean getFileList(String username){
        if(!basePath.containsKey(username)){
            FTPUser ftp = ftpUserMapper.selectFTPUser(username);
            basePath.put(username,ftp.getHomedirectory());
        }
        if(!currentPath.containsKey(username)){
            currentPath.put(username, new FilePath());
        }
        List<MyFile> list = FileUtils.turnFileList(new File(baseDir + basePath.get(username) + currentPath.get(username).toString()).listFiles());
        RespPageBean bean = new RespPageBean();
        bean.setData(list);
        bean.setTotal(Long.parseLong(String.valueOf(list.size())));
        return bean;
    }

    public RespBean changeDir(int type, String username, String path, String folder, int step){
        if(!verifyPath(path, username)){
            return RespBean.error("验证错误");
        }
        String ucp = currentPath.get(username).toString();
        String base = basePath.get(username);
        File file = new File(baseDir + base + ucp + "/" + folder);
        if(type == 1){
            if(!file.exists() || !file.isDirectory()){
                return RespBean.error("路径错误");
            }
            else{
                currentPath.get(username).addPath(folder);
                return RespBean.ok(29200, "进入成功");
            }
        }
        else if(type == 0){
            if(!folder.equals("</>")){
                return RespBean.error("指令错误");
            }
            FilePath fp = currentPath.get(username);
            if(!fp.deleteNumSuffix(step)){
                return RespBean.error("返回错误");
            }
            currentPath.put(username, fp);
            return RespBean.ok(29200, "退回成功");
        }
        return RespBean.error("未知错误");
    }

    public RespBean deleteFile(String username, String[] list, String path){
        if(!verifyPath(path, username)){
            return RespBean.error("验证出错");
        }
        String ucp = currentPath.get(username).toString();
        String base = basePath.get(username);
        File file;
        for(String t : list){
            file = new File(baseDir + base + ucp + "/" + t);
            if(file.exists()){
                if(!FileUtils.deleteDir(file)){
                    RespBean.error("出现错误");
                }
            }
        }
        return RespBean.ok("删除成功");
    }

    public RespBean uploadFile(String username, MultipartFile file, String paths, String path){
        if(!verifyPath(path, username)){
            return RespBean.error("验证错误");
        }
        String ucp = currentPath.get(username).toString();
        String base = basePath.get(username);
        RespBean bean;
        bean = fileService.saveFiles(file, paths,baseDir + base + ucp);
        if(bean.getCode() == 500){
            return bean;
        }
        return RespBean.ok(29200, "上传成功");
    }

    public RespBean createFolder(String username, String path, String folder){
        if(!verifyPath(path, username)){
            return RespBean.error("验证失败");
        }
        String ucp = currentPath.get(username).toString();
        String base = basePath.get(username);
        File file = new File(baseDir + base + ucp + "/" + folder);
        MyLogger.logger(baseDir + base + ucp + "/" + folder);
        if(file.exists()){
            return RespBean.error("文件夹已经存在");
        }
        if(file.mkdir()){
            return RespBean.ok("创建成功");
        }
        return RespBean.error("未知错误");
    }

    public RespBean downloadFile(String username, String path, String filename, HttpServletResponse response){
        if(!verifyPath(path, username)){
            return RespBean.error("验证错误");
        }
        String ucp = currentPath.get(username).toString();
        String base = basePath.get(username);
        File file = new File(baseDir + base + ucp + "/" + filename);
        if(!file.exists()){
            return RespBean.error("文件不存在");
        }
        if(file.isDirectory()){
            return RespBean.error("不让下载文件夹哦");
        }
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return RespBean.error("未知错误");
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error("写入错误");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return RespBean.ok("下载成功");
    }

    private boolean verifyPath(String path, String username){
        return path.equals(currentPath.get(username).toString());
    }
}
