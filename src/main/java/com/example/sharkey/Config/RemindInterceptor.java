package com.example.sharkey.Config;

import com.example.sharkey.Annotation.Remind;
import com.example.sharkey.Utils.IpUtil;
import com.example.sharkey.Watcher.Service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
@Slf4j
public class RemindInterceptor implements HandlerInterceptor {
    @Autowired
    private MailService mailService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        boolean hasAnnotation = handler.getClass().isAssignableFrom(HandlerMethod.class);
        if(hasAnnotation){
            Remind iv = ((HandlerMethod)handler).getMethodAnnotation(Remind.class);
            if(iv != null){
                String subject = "有用户正在尝试做一些值得你注意的事情";
                StringBuffer content = new StringBuffer();
                content.append("IP:").append(IpUtil.getIpAddr(request)).append('\n');
                content.append("URL:").append(request.getRequestURL()).append('\n');
                mailService.remindDeveloper(subject, content.toString());
            }
        }
    }
}
