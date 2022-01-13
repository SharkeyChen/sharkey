package com.example.sharkey.Task;

import com.alibaba.fastjson.JSONObject;
import com.example.sharkey.Entity.Base.RequestType;
import com.example.sharkey.Entity.Base.URLPackage;
import com.example.sharkey.Entity.Memo;
import com.example.sharkey.Entity.Notify;
import com.example.sharkey.Entity.User;
import com.example.sharkey.Entity.VoteConfig;
import com.example.sharkey.Utils.EncodeUtils;
import com.example.sharkey.Utils.HttpClientUtils;
import com.example.sharkey.Utils.JsonUtils;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.NotifyMapper;
import com.example.sharkey.Watcher.Mapper.UserMapper;
import com.example.sharkey.Watcher.Mapper.VoteMapper;
import com.example.sharkey.Watcher.Service.MailService;
import com.example.sharkey.Watcher.Service.NotifyService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@EnableScheduling
@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ScheduledTask {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private VoteMapper voteMapper;

    private final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 0 8 * * ?")
    public void SendMemMail(){
        List<Memo> list = userMapper.getMemoToday();
        for(int i = 0;i < list.size();i ++){
            if(StringUtils.isBlank(list.get(i).getEmail())){
                continue;
            }
            StringBuffer res = new StringBuffer();
            res.append("<font size=\"4\">#nickname#：</font><div>&nbsp; &nbsp; &nbsp; &nbsp; " +
                    "你好，这是来自SKSG的一封邮件，你能收到这封邮件是因为你在本站设置了一个备忘录，" +
                    "其内容如下：</div><div style=\"text-align: center;\"><font size=\"5\">#content#" +
                    "</font></div><div style=\"text-align: left;\"><font size=\"2\">&nbsp; " +
                    "&nbsp; &nbsp; &nbsp; &nbsp;请好好回想一下，最后感谢使用SKSG，献上最诚挚的敬意！" +
                    "</font></div><div style=\"text-align: right;\"><font size=\"2\">" +
                    "————Sharkey:the developer of SKSG</font></div>");
            String content = res.toString();
            Memo temp = list.get(i);
            content = content.replace("#nickname#", temp.getNickname());
            content = content.replace("#content#", temp.getMemo());
            mailService.sendHTMLMail(list.get(i).getEmail(), "来至于SKSG的备忘录提醒哦", content);
        }
    }

    @Scheduled(cron = "0 30 23 * * ?")
    public void SendNotificationEmail(){
        Date now = new Date();
        List<Notify> list = notifyMapper.getNotificationToday(now);
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        for(Notify notify : list){
            for(User user : notify.getToUser()){
                if(StringUtils.isBlank(user.getEmail()) || !p.matcher(user.getEmail()).matches()){
                    continue;
                }
                String content = "<font size=\"4\">#nickname#：</font><div>&nbsp; &nbsp; &nbsp; &nbsp; " +
                        "<font size=\"2\" style=\"\">你好，这是来自SKSG的一封邮件，这里有一个本站的邮件通知，请您查收，内容如下：" +
                        "</font></div><div><font size=\"2\" style=\"\"><br></font></div><div style=\"text-align: left;\">" +
                        "<font size=\"5\">&nbsp; &nbsp; &nbsp;#content#</font></div><div style=\"text-align: left;\"><font size=\"5\">" +
                        "<br></font></div><div style=\"text-align: left;\"><font size=\"2\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" +
                        "感谢使用SKSG，献上最诚挚的敬意！</font></div><div style=\"text-align: right;\"><font size=\"2\">————Sharkey:the developer of SKSG" +
                        "</font></div>";
                content = content.replace("#nickname#", user.getNickname());
                content = content.replace("#content#", notify.getMessage());
                mailService.sendHTMLMail(user.getEmail(), "来至于SKSG的通知", content);
            }
        }
    }

    @Scheduled(cron ="0 0 8 * * ?")
    public void VoteScheduled(){
        List<VoteConfig> votes = voteMapper.getVerifyVote();
        if(votes == null){
            return ;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        int sucCount = 0;
        StringBuffer sucV = new StringBuffer();
        int failCount = 0;
        StringBuffer failV = new StringBuffer();
        int sendCount = 0;
        StringBuffer sucS = new StringBuffer();
        int failSend = 0;
        StringBuffer failS = new StringBuffer();
        for(VoteConfig vc : votes){
            if(!vc.getEnable() || StringUtils.isBlank(vc.getUser().getEmail()) || !p.matcher(vc.getUser().getEmail()).matches()){
                continue;
            }
            int code = sendVoteRequest(vc);
            String result;
            if(code == 200){
                sucCount ++;
                sucV.append(vc.getUser().getNickname()).append("; ");
            }
            else{
                failCount ++;
                failV.append(vc.getUser().getNickname()).append("; ");
            }
            result = getMessage(code);
            boolean isYes = sendVoteMail(vc.getUser(), result);
            if(isYes){
                sendCount ++;
                sucS.append(vc.getUser().getNickname()).append("; ");
            }
            else{
                failSend ++;
                failS.append(vc.getUser().getNickname()).append("; ");
            }
        }
        mailService.sendMail("1538720091@qq.com", "今日自动打卡结果展示",
                String.format("打卡成功：%d\n%s\n打卡失败：%d\n%s\n提醒成功：%d\n%s\n提醒失败:%d\n%s\n", sucCount, sucV,
                        failCount, failV, sendCount, sucS, failSend, failS));
    }

    public int sendVoteRequest(VoteConfig vote){
        URLPackage urlPackage = new URLPackage("https://reserve.25team.com/wxappv1/yi/addReport");
        urlPackage.setRequestType(RequestType.POST);
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/json");
        headers.put("Accept-Encoding","gzip, deflate, br\n");
        headers.put("Referer", "https://servicewechat.com/wxd2bebfc67ee4a7eb/63/page-frame.html");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        headers.put("token", vote.getToken());
        urlPackage.setHeaders(headers);
        urlPackage.setEntity(getMyEntity(vote));
        HttpResponse response = HttpClientUtils.httpPostRequest(urlPackage);
        JSONObject res = null;
        try{
            String str = EntityUtils.toString(response.getEntity());
            String encode = EncodeUtils.getEncoding(str);
            if (!(encode.equals("utf-8") || encode.equals("UTF-8") || encode.equals("GB2312"))) {
                str = new String(str.getBytes(encode), StandardCharsets.UTF_8);
            }
            res = JsonUtils.JsonToObject(str);
            log.info(res.toJSONString());
            return Integer.parseInt(res.getString("code"));
        }catch (Exception e){
            e.printStackTrace();
            return 600;
        }
    }

    private String getMyEntity(VoteConfig vote){
        JSONObject entity = new JSONObject();
        JSONObject content = new JSONObject();
        for(int i = 0;i < 35;i ++){
            content.put(String.valueOf(i), "");
        }
        content.put("0", "在京在校-集中住宿");
        content.put("1", vote.getApartment());
        content.put("6", vote.getAddress() + " 经纬度:" + vote.getLng() + "," + vote.getLat());
        content.put("7", "否");
        content.put("8", "37.3以下");
        content.put("9", "正常");
        content.put("10", "否");
        content.put("13", "否");
        content.put("15", "否");
        content.put("16", "均正常");
        content.put("17", "否");
        content.put("18", "否");
        content.put("21", "否");
        content.put("22", "否");
        content.put("23", "否");
        content.put("24", "是");
        content.put("25", vote.getFirst());
        content.put("27", "是");
        content.put("28", vote.getSecond());
        content.put("30", "是");
        content.put("31", vote.getThird());
        entity.put("content", content);
        entity.put("version", 20);
        entity.put("stat_content",new JSONObject());
        JSONObject loc = new JSONObject();
        loc.put("country", "中国");
        loc.put("province", vote.getProvince());
        loc.put("city", "");
        loc.put("longitude", vote.getLng());
        loc.put("latitude", vote.getLat());
        entity.put("location", loc);
        entity.put("sick", "");
        entity.put("accept_templateid", "");
        log.info(entity.toJSONString());
        return entity.toJSONString();
    }

//    private boolean isSchool(double lng, double lat){
//        return lng >= 116.230 && lng <= 116.270 && lat >= 40.210 && lat <= 40.230;
//    }

    private String getMessage(int code){
        if(code == 200){
            return "成功";
        }
        else if(code == 20001){
            return "失败（Token无效，请重新获取Token）";
        }
        else if(code == 400){
            return "失败";
        }
        else if(code == 500){
            return "已经打过卡了，不需要本站打卡了";
        }
        else{
            return "未知错误";
        }
    }

    private boolean sendVoteMail(User user, String res){
        String content = "<font size=\"4\">#nickname#：</font><div>&nbsp; &nbsp; &nbsp; &nbsp;<font size=\"\\&quot;2\\&quot;\" style=\"\\&quot;\\&quot;\">你好，这是来自SKSG的一封邮件，您的今日打卡情况如下：</font></div><div><font size=\"\\&quot;2\\&quot;\" style=\"\\&quot;\\&quot;\"><br></font></div><div style=\"text-align: center; \" left;\\\"=\"\"><font size=\"5\">#content#</font></div><div style=\"text-align: center; \" left;\\\"=\"\"><font size=\"\\&quot;5\\&quot;\"><br></font></div><div style=\"text-align: left;\" left;\\\"=\"\"><font color=\"#ff0000\">&nbsp; &nbsp; &nbsp; &nbsp;<font size=\"4\">注意！如果有身体不适，请尽快去校医院并且与辅导员联系情况，并且关闭自动打卡功能。</font></font></div><div style=\"text-align: left;\" left;\\\"=\"\"><font color=\"#ff0000\"><font size=\"4\"><br></font></font></div><div style=\"\\&quot;text-align:\" left;\\\"=\"\"><font size=\"\\&quot;2\\&quot;\">&nbsp; &nbsp; &nbsp; &nbsp;感谢使用SKSG，献上最诚挚的敬意！Long May the Sunshine！</font></div><div style=\"text-align: right; \" right;\\\"=\"\"><font size=\"\\&quot;2\\&quot;\">————Sharkey:the developer of SKSG</font></div>";
        content = content.replace("#nickname#", user.getNickname());
        content = content.replace("#content#", res);
        return mailService.sendHTMLMail(user.getEmail(), "来至于SKSG的自动打卡结果提醒", content);
    }
}
