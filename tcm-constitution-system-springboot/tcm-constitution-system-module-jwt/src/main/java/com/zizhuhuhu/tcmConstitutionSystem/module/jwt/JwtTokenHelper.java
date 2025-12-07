package com.zizhuhuhu.tcmConstitutionSystem.module.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
@Component
public class JwtTokenHelper implements InitializingBean {
    /**
     *
     * 签发人
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * 密钥
     */
    private Key key;

    /**
     * JWT解析
     */
    private JwtParser jwtParser;

    /**
     *
     * 解码配置文件中配置的Base64编码key为密钥
     * @param base64key
     */
    @Value("${jwt.secret}")
    public void setBase64key(String base64key){
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64key));
    }

    /**
     * 初始化 JwtParser
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }
    /**
     * 生成Token
     * @param username
     * @return
     */
    @Value("${jwt.tokenExpireTime}")
    private Long tokenExpireTime;
    public String generateToken(String username){
        LocalDateTime now = LocalDateTime.now();
        //Token一个小时后生效
        LocalDateTime expireTime = now.plusHours(tokenExpireTime);

        return Jwts.builder().setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact();
    }
    /**
     * 解析Token
     * @param token
     * @return
     */
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 生成一个 Base64 的安全秘钥
     * @return
     */
    private static String generateBase64Key() {
        // 生成安全秘钥
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // 将密钥进行 Base64 编码
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        return base64Key;
    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println("key: " + key);
    }
    /**
     * 校验Token是否可用
     * @Param token
     * @return
     */
    public void validateToken(String token){
        jwtParser.parseClaimsJws(token);
    }
    /**
     * 解析Token获取用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
