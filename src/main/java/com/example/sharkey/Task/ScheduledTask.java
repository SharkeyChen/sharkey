package com.example.sharkey.Task;

import com.example.sharkey.Entity.Memo;
import com.example.sharkey.Entity.Notify;
import com.example.sharkey.Entity.User;
import com.example.sharkey.Watcher.Mapper.NotifyMapper;
import com.example.sharkey.Watcher.Mapper.UserMapper;
import com.example.sharkey.Watcher.Service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private NotifyMapper notifyMapper;

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
}
