package com.example.sharkey.Task;

import com.example.sharkey.Entity.Memo;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.UserMapper;
import com.example.sharkey.Watcher.Service.MailService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class MemTask {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    private final Logger logger = LoggerFactory.getLogger(MemTask.class);

    @Scheduled(cron = "0 0 8 * * ?")
    public void SendMailToRemind(){
        List<Memo> list = userMapper.getMemoToday();
        for(int i = 0;i < list.size();i ++){
            if(StringUtils.isBlank(list.get(i).getEmail())){
                continue;
            }
            logger.info(list.get(i).getMemo());
            StringBuffer res = new StringBuffer();
            res.append("尊敬的").append(list.get(i).getUsername()).append(":\n");
            res.append("          您有个备忘录，其内容是“").append(list.get(i).getMemo()).append("”,请想回想一下哦");
            res.append("                                       --------------SKSG小助手");
            mailService.sendMail(list.get(i).getEmail(), "来至于SKSG的备忘录提醒哦", res.toString());
        }
    }
}
