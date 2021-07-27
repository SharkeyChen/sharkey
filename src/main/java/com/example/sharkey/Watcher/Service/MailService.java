package com.example.sharkey.Watcher.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String Sender;

    public void remindDeveloper(String subject, String content){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(Sender);
        mail.setTo("1538720091@qq.com");
        mail.setSubject(subject);
        mail.setText(content);
        mailSender.send(mail);
    }

    public void sendMail(String to, String subject, String content){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(Sender);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        mailSender.send(mail);
    }

    public boolean sendHTMLMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
//            System.out.println("html格式邮件发送失败");
            return false;
        }
    }

//    public boolean
}
