package com.example.sharkey.Config;

/**
 * Author: Sharkey
 * Date: 2020/8/1
 */

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Utils.TokenUtils;
import com.sun.net.httpserver.HttpsServer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url = request.getRequestURI();
        MyLogger.logger(url);
        String token = request.getHeader("Authorization");
        MyLogger.logger(token);
        String checkHeader = request.getHeader("checkAuth");
        MyLogger.logger(checkHeader);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        if(checkHeader.equals("false")){
            return true;
        }
        if(!TokenUtils.Verify(token)){
            response.setStatus(200);
            response.getWriter().append(RespBean.error(700, "身份验证有误，请重新登陆吧").toString());
            return false;
        }
        return true;
    }
}
