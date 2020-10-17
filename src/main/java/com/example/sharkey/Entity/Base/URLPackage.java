package com.example.sharkey.Entity.Base;

import com.alibaba.fastjson.JSONObject;

import java.net.URI;

/**
 * Author：Sharkey
 * Date: 2020/10/06
 * Des：请求封装类，包括URI,Header,URLEncode
 */

public class URLPackage {
    private String url;

    private JSONObject headers;

    private JSONObject URLEncode;

    private RequestType requestType;

    private String entity;


    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public URLPackage(){
    }

    public URLPackage(String url){
        this.url = url;
    }

    public URLPackage(String url, RequestType requestType){
        this.url = url;
        this.requestType = requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(JSONObject headers) {
        this.headers = headers;
    }

    public void setURLEncode(JSONObject URLEncode) {
        this.URLEncode = URLEncode;
    }

    public String getUrl() {
        return url;
    }

    public JSONObject getHeaders() {
        return headers;
    }

    public JSONObject getURLEncode() {
        return URLEncode;
    }

    public URI getURI() {
        StringBuffer buffer = new StringBuffer(this.url);
        buffer.append("?");
        if(this.URLEncode != null){
            for(JSONObject.Entry<String, Object> entry : this.URLEncode.entrySet()){
                buffer.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
            }
        }
        URI res = null;
        try{
            res = new URI(buffer.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
