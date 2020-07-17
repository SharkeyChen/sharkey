package com.example.sharkey.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLogger extends Object{
    public static void logger(String message){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(String.format("[%s] Info : %s",df.format(new Date()), message));
    }
}
