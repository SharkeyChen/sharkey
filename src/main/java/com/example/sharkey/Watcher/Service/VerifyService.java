package com.example.sharkey.Watcher.Service;

import com.example.sharkey.Entity.RespBean;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Utils.TokenUtils;
import com.example.sharkey.Watcher.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserMapper userMapper;

    public RespBean sendVerifyEmail(String username, String email){
        String token = TokenUtils.getEmailShortToken(username, email);
        String content = "<font size=\"\\&quot;4\\&quot;\">#nickname#：</font><div>&nbsp; &nbsp; &nbsp; &nbsp;<font size=\"\\&quot;\\\\&quot;2\\\\&quot;\\&quot;\" style=\"\\&quot;\\\\&quot;\\\\&quot;\\&quot;\">你好，这是来自SKSG的一封邮件，验证邮箱的Token如下，请复制粘贴到验证输入框，有效时间3min：</font></div><div><font size=\"\\&quot;\\\\&quot;2\\\\&quot;\\&quot;\" style=\"\\&quot;\\\\&quot;\\\\&quot;\\&quot;\"><br></font></div><div style=\"text-align: center; \" center;=\"\" \\\"=\"\" left;\\\\\\\"=\"\\&quot;\\&quot;\"><font size=\"4\">#content#</font></div><div style=\"\\&quot;text-align:\" center;=\"\" \\\"=\"\" left;\\\\\\\"=\"\\&quot;\\&quot;\"><font size=\"\\&quot;\\\\&quot;5\\\\&quot;\\&quot;\"><br></font></div><div style=\"\\&quot;text-align:\" left;\\\"=\"\" left;\\\\\\\"=\"\\&quot;\\&quot;\"><font color=\"\\&quot;#ff0000\\&quot;\">&nbsp; &nbsp; &nbsp; &nbsp;<font size=\"\\&quot;4\\&quot;\"></font></font></div><div style=\"\\&quot;text-align:\" left;\\\"=\"\" left;\\\\\\\"=\"\\&quot;\\&quot;\"><font color=\"\\&quot;#ff0000\\&quot;\"><font size=\"\\&quot;4\\&quot;\"><br></font></font></div><div style=\"\\&quot;\\\\&quot;text-align:\\&quot;\" left;\\\\\\\"=\"\\&quot;\\&quot;\"><font size=\"\\&quot;\\\\&quot;2\\\\&quot;\\&quot;\">&nbsp; &nbsp; &nbsp; &nbsp;感谢使用SKSG，献上最诚挚的敬意！Long May the Sunshine！</font></div><div style=\"text-align: right; \" right;=\"\" \\\"=\"\" right;\\\\\\\"=\"\\&quot;\\&quot;\"><font size=\"\\&quot;\\\\&quot;2\\\\&quot;\\&quot;\">————Sharkey:the developer of SKSG</font></div>\n";
        content = content.replace("#nickname#", username);
        content = content.replace("#content#", token);
        boolean flag = mailService.sendHTMLMail(email, "SKSG的邮箱验证", content);
        if(flag){
            return RespBean.ok("邮件已发送");
        }
        else{
            return RespBean.error("位置错误");
        }
    }

    public RespBean verifyToken(String token, String username){
        MyLogger.logger(username);
        if(!TokenUtils.Verify(token)){
            return RespBean.error("验证无效");
        }
        try{
            userMapper.updateCheckEmailByUsername(username);
            return RespBean.ok("验证成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("验证失败");
        }
    }
}
