package com.example.sharkey.Config;

import com.alibaba.fastjson.JSONObject;
import com.example.sharkey.Annotation.Remind;
import com.example.sharkey.Entity.IpFilter;
import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Utils.IpUtil;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.IpFilterMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class IpInterceptor implements HandlerInterceptor {

    @Autowired
    private IpFilterMapper ipFilterMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String ip = IpUtil.getIpAddr(request);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        if(!StringUtils.isNotBlank(ip)){
            response.setStatus(200);
            response.getWriter().append(RespBean.error(600, "无效的IP").toString());
            return false;
        }
        int res = ipFilterMapper.getFilter(ip);
        if(res != 0){
            response.setStatus(200);
            response.getWriter().append(RespBean.error(600, "你已经被本站管理员封禁，反思反思为神魔吧").toString());
            return false;
        }
        return true;
    }
}
