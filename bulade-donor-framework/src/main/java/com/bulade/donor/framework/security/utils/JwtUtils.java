package com.bulade.donor.framework.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    /**
     * 两个常量： 过期时间；秘钥
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 24;

    public static final String SECRET = "camsdgdgdfsfsssssssssssssssssssssssssdfpus2022";

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtils.SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
    }

    /**
     * 生成token字符串的方法
     */
    public static String generateToken(Map<String, ?> claims) {
        return Jwts.builder()
            .claims(claims)
            .subject("lin-user")
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + EXPIRE))
            .signWith(generalKey())
            .compact();
    }

    public static Claims extractClaims(String token) {
        return Jwts.parser()
            .setSigningKey(generalKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public static <T> T getByKey(String token, String key, Class<T> tClass) {
        var claims = extractClaims(token);
        return claims.get(key, tClass);
    }

    public static Map<String, String> toMap(String token) {
        SecretKey secretKey = generalKey();
        var builder = Jwts.parser().setSigningKey(secretKey).build();
        Jws<Claims> claimsJws = builder.parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        var map = new HashMap<String, String>();
        body.forEach((k, v) -> map.put(k, String.valueOf(v)));
        return map;
    }

    /**
     * 验证令牌是否过期
     */
    public static boolean isNotValid(String token) {
        try {
            var claims = extractClaims(token);
            return new Date().after(claims.getExpiration());
        } catch (Exception e) {
            return true;
        }
    }

}

