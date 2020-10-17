package com.example.sharkey.Utils;

import com.alibaba.fastjson.JSONObject;
import com.example.sharkey.Entity.Base.RequestType;
import com.example.sharkey.Entity.Base.URLPackage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


public class HttpClientUtils {
    /**
     * DES：发送请求连接，返回HttpResponse的返回体，
     * @param up
     * @return
     */
    public static HttpResponse httpSendRequest(URLPackage up){
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpRequestBase method = getRequestByType(up.getRequestType());
        HttpResponse response = null;
        if(method == null){
            System.out.println("没有请求类型");
            return null;
        }
        method.setURI(up.getURI());
        JSONObject headers = up.getHeaders();
        if(headers != null){
            for(JSONObject.Entry<String, Object> entry: headers.entrySet()){
                method.setHeader(entry.getKey(), (String)entry.getValue());
            }
        }
        try{
            response = httpClient.execute(method);
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse httpPostRequest(URLPackage up){
        try{
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost();
            HttpResponse response = null;
            if(up.getRequestType() != RequestType.POST){
                System.out.println("方法类型不准");
                return null;
            }
            post.setURI(up.getURI());
            JSONObject headers = up.getHeaders();
            if(headers != null){
                for(JSONObject.Entry<String, Object> entry: headers.entrySet()){
                    post.setHeader(entry.getKey(), (String)entry.getValue());
                }
            }
            if(up.getEntity() != null){
                post.setEntity(new StringEntity(up.getEntity(), "application/json","UTF-8"));
            }
            response = httpClient.execute(post);
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




    private static HttpRequestBase getRequestByType(RequestType requestType){
        switch (requestType){
            case GET:
                return new HttpGet();
            case PUT:
                return new HttpPut();
            case POST:
                return new HttpPost();
            case DELETE:
                return new HttpDelete();
            default:
                return null;
        }
    }

}
