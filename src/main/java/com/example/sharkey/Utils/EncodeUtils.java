package com.example.sharkey.Utils;

/**
 * Author:Sharkey
 * Date:2020/09/23
 * 关于字符串编码集的工具类
 */

public class EncodeUtils {

    private static String[] encodeList = {"GB2312", "ISO-8859-1", "UTF-8", "GBK"};

    /**
     * 获取字符串的编码集,如果不存在，则返回一个空字符串
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        for(int i = 0;i < encodeList.length;i ++){
            if(encodeString(str, encodeList[i])){
                return encodeList[i];
            }
        }
        return "";
    }

    /**
     * 判断字符串的编码即与传入参数是否一致
     * @param str
     * @param encode
     * @return
     */
    private static boolean encodeString(String str, String encode){
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GB2312
                return true;
            }
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

}