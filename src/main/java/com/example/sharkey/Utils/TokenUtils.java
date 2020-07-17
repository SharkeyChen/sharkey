package com.example.sharkey.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc Token验证登陆工具类
 * @author Sharkey
 * @Date 2020.07.16
 */

public class TokenUtils {
    // 过期时间
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 15;
    // Token秘钥
    private static final String TOKEN_SECRET = "vQ4rxAqUPDmGRa8txwPQXrE9IYQtn3qb";


    /**
     * 生成Token
     * @param username
     * @param password
     * @return
     */
    public static String getToken(String username, String password){
        String token = "";
        try{
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //头部信息
            Map<String, Object> header = new HashMap();
            header.put("typ", "JWT");
            header.put("alg", "HS256");

            token = JWT.create().withHeader(header)
                    .withClaim("username", username)
                    .withClaim("password", password)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            MyLogger.logger(e.getMessage());
            MyLogger.logger("Token生成失败！");
        }
        return token;
    }


    /**
     * 验证Token
     * @param token
     * @return
     */
    public static boolean Verify(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            MyLogger.logger(e.getMessage());
            MyLogger.logger("验证Token过程中出现错误TQT");
        }
        return false;
    }

}
