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
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@EnableScheduling
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
        int failCount = 0;
        int sendCount = 0;
        int failSend = 0;
        for(VoteConfig vc : votes){
            if(!vc.getEnable() || StringUtils.isBlank(vc.getUser().getEmail()) || !p.matcher(vc.getUser().getEmail()).matches()){
                continue;
            }
            int code = sendVoteRequest(vc.getToken());
            MyLogger.logger(code + " " + vc.getToken());
            String result;
            if(code == 200){
                sucCount ++;
            }
            else{
                failCount ++;
            }
            result = getMessage(code);
            boolean isYes = sendVoteMail(vc.getUser(), result);
            if(isYes){
                sendCount ++;
            }
            else{
                failSend ++;
            }
        }
        mailService.sendMail("1538720091@qq.com", "今日自动打卡结果展示",
                String.format("打卡成功：%d 打卡失败：%d\n提醒成功：%d 提醒失败:%d\n", sucCount, failCount, sendCount, failSend));
    }

    public int sendVoteRequest(String token){
        URLPackage urlPackage = new URLPackage("https://reserve.25team.com/wxappv1/yi/addReport");
        urlPackage.setRequestType(RequestType.POST);
        urlPackage.setEntity("{\"content\":{\"0\":\"在京，在校集中住宿\",\"1\":\"之前已返校或未离校\",\"2\":\"\",\"3\":\"\",\"4\":\"\",\"5\":\"低风险\",\"6\":\"北京市昌平区城北街道中共北京市昌平区委员会北京市昌平区人民政府 经纬度:116.23128,40.22077\",\"7\":\"正常\",\"8\":\"37.3以下\",\"9\":\"绿色\",\"10\":\"均正常\",\"11\":\"无\",\"12\":\"否\",\"13\":\"\",\"14\":\"\"},\"version\":16,\"stat_content\":{},\"location\":{\"province\":\"北京市\",\"country\":\"中国\",\"city\":\"\",\"longitude\":116.23128,\"latitude\":40.22077},\"sick\":\"\",\"accept_templateid\":\"\"}");
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/json");
        headers.put("Accept-Encoding","gzip, deflate, br\n");
        headers.put("Referer", "https://servicewechat.com/wxd2bebfc67ee4a7eb/63/page-frame.html");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        headers.put("token", token);
        urlPackage.setHeaders(headers);
        HttpResponse response = HttpClientUtils.httpPostRequest(urlPackage);
        JSONObject res = null;
        try{
            String str = EntityUtils.toString(response.getEntity());
            String encode = EncodeUtils.getEncoding(str);
            if (!(encode.equals("utf-8") || encode.equals("UTF-8") || encode.equals("GB2312"))) {
                str = new String(str.getBytes(encode), StandardCharsets.UTF_8);
            }
            res = JsonUtils.JsonToObject(str);
            JsonUtils.printJObject(res);
            return Integer.parseInt(res.getString("code"));
        }catch (Exception e){
            e.printStackTrace();
            return 600;
        }

    }

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
