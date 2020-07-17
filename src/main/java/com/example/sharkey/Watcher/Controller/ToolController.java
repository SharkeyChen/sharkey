package com.example.sharkey.Watcher.Controller;

import com.example.sharkey.Utils.IpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tool")
public class ToolController {
    @GetMapping("/getIp")
    public String getIp(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        return ip;
    }
}
