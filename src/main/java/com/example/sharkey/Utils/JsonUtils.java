package com.example.sharkey.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Author：Sharkey
 * Date：2020/09/24
 * 处理JSONObject的工具类
 */

public class JsonUtils {

    /**
     * 将字符串转换为JSONObject
     * @param json
     * @return JSONObject
     */
    public static JSONObject JsonToObject(String json){
        JSONObject res = null;
        try{
            res = JSONObject.parseObject(json);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSONObject格式化输出到控制台
     * @param jb
     * @param
     */
    public  static void printJObject(JSONObject jb, StringBuffer prefix){
        if(jb == null){
            return ;
        }
        System.out.println(prefix.toString() + "{");
        prefix.append("\t");
        for(JSONObject.Entry<String, Object> entry : jb.entrySet()){
            if(entry.getValue() instanceof JSONObject){
                System.out.println(prefix.toString() + entry.getKey() + ":");
                printJObject((JSONObject)entry.getValue(), prefix);
            }
            else if(entry.getValue() == null){
                System.out.println(prefix.toString() + entry.getKey() + ": null");
            }
            else if(entry.getValue() instanceof JSONArray){
                System.out.println(prefix.toString() + entry.getKey() + ":[");
                prefix.append("\t");
                JSONArray tep = (JSONArray)entry.getValue();
                if(tep.size() > 0){
                    if(tep.get(0) instanceof JSONObject){
                        for(int i = 0;i < tep.size();i ++){
                            printJObject(tep.getJSONObject(i), prefix);
                            System.out.println(prefix.toString() + ",");
                        }
                    }
                    else{
                        for(int i = 0;i < tep.size();i ++){
                            System.out.println(prefix.toString() + tep.getString(i) + ",");
                        }
                    }
                }
                prefix.deleteCharAt(prefix.length() - 1);
                System.out.println(prefix.toString() + "]");
            }
            else{
                System.out.println(prefix.toString() + entry.getKey() + " : " +entry.getValue().toString());
            }
        }
        prefix.deleteCharAt(prefix.length() - 1);
        System.out.println(prefix.toString() + "}");
    }

    public static void printJObject(JSONObject jb){
        printJObject(jb, new StringBuffer());
    }
}
