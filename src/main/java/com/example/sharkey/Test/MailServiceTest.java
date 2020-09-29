package com.example.sharkey.Test;

import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailServiceTest {
    @Autowired
    private MailService mailService;

    @Test
    public void mail(){
        MyLogger.logger("发送了");
        mailService.sendMail("1538720091@qq.com", "测试", "还是测试");
    }
}
