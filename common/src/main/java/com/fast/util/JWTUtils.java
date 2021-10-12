package com.fast.util;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.fast.enums.ErrorEnum;
import com.fast.model.BiteClaims;
import com.fast.model.FastRunTimeException;
import com.fast.model.RandomKeyResponse;
import com.fast.model.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author Martin
 * @Date 2021/8/6 19:07
 * @remark
 */
@Log4j2
public class JWTUtils {

    static RedisUtils redisUtils = SpringUtil.getBean(RedisUtils.class);
    private final static String ACCOUNT_ID = "accountId";
    private final static String TENANT_ID = "tenantId";
    private final static String KEY = "key";
    private final static String OVERDUE = "overdue";
    private final static String LOGIN_TIME = "loginTime";
    private final static String VALIDITY_TIME = "validityTime";
    private final static String TOKEN_TYPE = "tokenType";
    private final static String ACCESS_TOKEN_TYPE = "accessToken";
    private final static String REFRESH_TOKEN_TYPE = "refreshToken";
    // tempToken有效时间：30分钟
    private final static Long TEMP_TOKEN_VALIDITY_TIME = 1000L * 60L * 30L;
    // accessToken有效时间：6小时
    private final static Long ACCESS_TOKEN_VALIDITY_TIME = 1000L * 60L * 60L * 6;
    // refreshToken有效时间：14天
    private final static Long REFRESH_TOKEN_VALIDITY_TIME = 1000L * 60L * 60L * 24 * 14;
    // token-redis前缀
    private final static String TOKEN_REDIS_KEY = "token:";
    // tokenId-redis前缀
    private final static String TOKEN_REDIS_ID = "tokenId:";

    public JWTUtils(){
        this.redisUtils = SpringUtil.getBean(RedisUtils.class);
    }

    /**
     * 根据key生成验证令牌(overdue:true表示会过期， false表示不会过期)
     */
    public static String getTempToken(String key, Boolean overdue) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(KEY, key);
        map.put(OVERDUE, overdue);
        map.put(VALIDITY_TIME, System.currentTimeMillis() + TEMP_TOKEN_VALIDITY_TIME);
        RandomKeyResponse randomKey = JWTKeys.getRandomKey();
        String jws = Jwts.builder().setClaims(map).signWith(randomKey.getKey()).compact();
        return randomKey.getMapKey() + jws;
    }

    /**
     * 获取tempToken的值
     * */
    public static String getTempTokenValue(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            String mapKey = token.substring(0, 1);
            String jws = token.substring(1, token.length());
            Claims claims = Jwts.parserBuilder().setSigningKey(JWTKeys.getKey(mapKey)).build().parseClaimsJws(jws).getBody();
            Long validityTime = claims.get(VALIDITY_TIME, Long.class);
            String validityKey = claims.get(KEY, String.class);
            Boolean aBoolean = claims.get(OVERDUE, Boolean.class);
            if (aBoolean){
                if (System.currentTimeMillis() > validityTime) {
                    return null;
                }
            }
            return validityKey;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证并解析TempToken(true:认证通过, false: 认证失败)
     */
    public static Boolean checkTempToken(String token, String key) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        Claims claims = null;
        try {
            String mapKey = token.substring(0, 1);
            String jws = token.substring(1, token.length());
            claims = Jwts.parserBuilder().setSigningKey(JWTKeys.getKey(mapKey)).build().parseClaimsJws(jws).getBody();
            Long validityTime = claims.get(VALIDITY_TIME, Long.class);
            String validityKey = claims.get(KEY, String.class);
            Boolean aBoolean = claims.get(OVERDUE, Boolean.class);
            if (aBoolean){
                if (System.currentTimeMillis() > validityTime || !validityKey.equals(key)) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据用户id，租户id生成accessToken，用户id不区分sasm、gp、investor，tenantId不存在则传null
     */
    private static String getAccessToken(Long accountId, Long tenantId) {
        long currentTimeMillis = System.currentTimeMillis();
        long validityTime = currentTimeMillis + ACCESS_TOKEN_VALIDITY_TIME;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ACCOUNT_ID, accountId);
        map.put(TENANT_ID, tenantId);
        map.put(LOGIN_TIME, currentTimeMillis);
        map.put(VALIDITY_TIME, validityTime);
        map.put(TOKEN_TYPE, ACCESS_TOKEN_TYPE);
        RandomKeyResponse randomKey = JWTKeys.getRandomKey();
        String jws = randomKey.getMapKey() + Jwts.builder().setClaims(map).signWith(randomKey.getKey()).compact();
        return jws;
    }

    /**
     * 根据refreshToken生成accessToken
     */
    private static String getAccessTokenByRefreshToken(String refreshToken) {
        // 调用checkToken对refreshToken进行验证和解析
        BiteClaims biteClaims = checkToken(refreshToken);
        if (REFRESH_TOKEN_TYPE.equals(biteClaims.getTokenType())) {
            return getAccessToken(biteClaims.getAccountId(), biteClaims.getTenantId());
        }
        throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
    }

    /**
     * 根据用户id，租户id生成refreshToken，用户id不区分sasm、gp、investor，tenantId不存在则传null
     */
    private static String getRefreshToken(Long accountId, Long tenantId) {
        long currentTimeMillis = System.currentTimeMillis();
        long validityTime = currentTimeMillis + REFRESH_TOKEN_VALIDITY_TIME;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ACCOUNT_ID, accountId);
        map.put(TENANT_ID, tenantId);
        map.put(LOGIN_TIME, currentTimeMillis);
        map.put(VALIDITY_TIME, validityTime);
        map.put(TOKEN_TYPE, REFRESH_TOKEN_TYPE);
        RandomKeyResponse randomKey = JWTKeys.getRandomKey();
        String jws = randomKey.getMapKey() + Jwts.builder().setClaims(map).signWith(randomKey.getKey()).compact();
        return jws;
    }

    /**
     * 根据用户id，租户id生成两个token，用户id不区分sasm、gp、investor，tenantId不存在则传null
     */
    public static TokenResponse getAccessTokenAndRefreshToken(Long accountId, Long tenantId) {
        long currentTimeMillis = System.currentTimeMillis();
        // 清除当前设备下当前用户旧的token
        String accessToken = getAccessToken(accountId, tenantId);
        String refreshToken = getRefreshToken(accountId, tenantId);
        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    /**
     * 根据refreshToken生成两个token
     */
    public static TokenResponse getAccessTokenAndRefreshTokenByRefreshToken(String refreshToken) {
        // 调用checkToken对refreshToken进行验证和解析
        BiteClaims biteClaims = checkToken(refreshToken);
        if (REFRESH_TOKEN_TYPE.equals(biteClaims.getTokenType())) {
            return getAccessTokenAndRefreshToken(biteClaims.getAccountId(), biteClaims.getTenantId());
        }
        throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
    }

    /**
     * 验证并解析token
     */
    public static BiteClaims checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
        }
        Claims claims = null;
        try {
            String mapKey = token.substring(0, 1);
            String jws = token.substring(1, token.length());
            claims = Jwts.parserBuilder().setSigningKey(JWTKeys.getKey(mapKey)).build().parseClaimsJws(jws).getBody();
            Long validityTime = claims.get(VALIDITY_TIME, Long.class);
            if (System.currentTimeMillis() > validityTime) {
                throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
            }
        } catch (Exception e) {
            throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
        }
        return BiteClaims.builder().accountId(claims.get(ACCOUNT_ID, Long.class))
                .tenantId(claims.get(TENANT_ID, Long.class))
                .times(claims.get(VALIDITY_TIME, Long.class))
                .tokenType(claims.get(TOKEN_TYPE, String.class))
                .loginTime(claims.get(LOGIN_TIME, Long.class)).build();
    }

    /**
     * 解析token
     */
    public static BiteClaims getBiteClaims(String token) {
        if (StringUtils.isBlank(token)) {
            throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
        }
        Claims claims = null;
        try {
            String mapKey = token.substring(0, 1);
            String jws = token.substring(1, token.length());
            claims = Jwts.parserBuilder().setSigningKey(JWTKeys.getKey(mapKey)).build().parseClaimsJws(jws).getBody();
            Long validityTime = claims.get(VALIDITY_TIME, Long.class);
            if (System.currentTimeMillis() > validityTime) {
                throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
            }
        } catch (Exception e) {
            throw new FastRunTimeException(ErrorEnum.Token过期或已失效);
        }
        return BiteClaims.builder().accountId(claims.get(ACCOUNT_ID, Long.class))
                .tenantId(claims.get(TENANT_ID, Long.class))
                .times(claims.get(VALIDITY_TIME, Long.class))
                .tokenType(claims.get(TOKEN_TYPE, String.class))
                .loginTime(claims.get(LOGIN_TIME, Long.class)).build();
    }
}
