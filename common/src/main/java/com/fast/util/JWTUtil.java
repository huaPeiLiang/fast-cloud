package com.fast.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    private final static String KEY = "key";

    /**
     * 创建Token
     * @param tokenType
     * @param ttlMillis 过期的时间长度
     */
    public static String createToken(String tokenType, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey key = new SecretKeySpec(KEY.getBytes(), 0, KEY.getBytes().length, "AES");
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("id", "id");
        claims.put("name", "name");
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(tokenType)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密Token
     * @param tokenType
     * @param jwt
     */
    public static Claims parseToken(String tokenType, String jwt) {
        try{
            SecretKey key = new SecretKeySpec(KEY.getBytes(), 0, KEY.getBytes().length, "AES");
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt).getBody();
            if (!claims.getId().equals(tokenType)){
                throw new RuntimeException("Token无效");
            }
            return claims;
        }catch (Exception e){
            throw new RuntimeException("Token无效或已失效");
        }
    }

}
