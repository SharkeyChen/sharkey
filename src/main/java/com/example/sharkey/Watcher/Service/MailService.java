package com.example.sharkey.Watcher.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String Sender;

    public void sendMail(String to, String subject, String content){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(Sender);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        mailSender.send(mail);
    }
}
