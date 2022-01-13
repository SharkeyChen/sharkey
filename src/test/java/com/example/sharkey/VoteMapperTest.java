package com.example.sharkey;

import com.alibaba.fastjson.JSONObject;
import com.example.sharkey.Entity.Base.RequestType;
import com.example.sharkey.Entity.Base.URLPackage;
import com.example.sharkey.Entity.VoteConfig;
import com.example.sharkey.Utils.EncodeUtils;
import com.example.sharkey.Utils.HttpClientUtils;
import com.example.sharkey.Utils.JsonUtils;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.VoteMapper;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class VoteMapperTest {
    @Autowired
    VoteMapper voteMapper;


    @Test
    public void test(){
        MyLogger.logger("yes");
        List<VoteConfig> list = voteMapper.getVerifyVote();
        sendVote(list.get(0));
        MyLogger.logger(list.size() + "");
    }

    private void sendVote(VoteConfig vote){
        URLPackage urlPackage = new URLPackage("https://reserve.25team.com/wxappv1/yi/addReport");
        urlPackage.setRequestType(RequestType.POST);
        JSONObject headers = new JSONObject();
        headers.put("content-type", "application/json");
        headers.put("Accept-Encoding","gzip, deflate, br\n");
        headers.put("Referer", "https://servicewechat.com/wxd2bebfc67ee4a7eb/63/page-frame.html");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        headers.put("token", vote.getToken());
        urlPackage.setHeaders(headers);
        JSONObject entity = new JSONObject();
        JSONObject content = new JSONObject();
        if(isSchool(vote.getLng(), vote.getLat())){
            content.put("0", "在京，在校集中住宿");
            content.put("1", "之前已返校或未离校");
        }
        else{
            content.put("0", "否");
            content.put("1", "");
        }
        content.put("2", "");
        content.put("3", "");
        content.put("4", "");
        content.put("5", "低风险");
        content.put("6", vote.getAddress() + " 经纬度:" + vote.getLng() + "," + vote.getLat());
        content.put("7", "正常");
        content.put("8", "37.3以下");
        content.put("9", "绿色");
        content.put("10", "均正常");
        content.put("11", "无");
        content.put("12", "否");
        content.put("13", "");
        content.put("14", "");
        entity.put("content", content);
        entity.put("stat_content",new JSONObject());
        JSONObject loc = new JSONObject();
        loc.put("country", "中国");
        loc.put("city", "");
        loc.put("longitude", vote.getLng());
        loc.put("latitude", vote.getLat());
        entity.put("location", loc);
        entity.put("sick", "");
        entity.put("accept_templateid", "");
        urlPackage.setEntity(entity.toJSONString());
        HttpResponse response = HttpClientUtils.httpPostRequest(urlPackage);
        JSONObject res = null;
        try{
            String str = EntityUtils.toString(response.getEntity());
            String encode = EncodeUtils.getEncoding(str);
            if (!(encode.equals("utf-8") || encode.equals("UTF-8") || encode.equals("GB2312"))) {
                str = new String(str.getBytes(encode), StandardCharsets.UTF_8);
            }
            res = JsonUtils.JsonToObject(str);
            JsonUtils.printJObject(res);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean isSchool(double lng, double lat){
        return lng >= 116.230 && lng <= 116.270 && lat >= 40.210 && lat <= 40.230;
    }

}
