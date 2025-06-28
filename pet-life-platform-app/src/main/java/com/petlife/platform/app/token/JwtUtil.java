package com.petlife.platform.app.token;

import com.petlife.platform.app.core.exception.CommonExceptionCode;
import com.petlife.platform.app.core.exception.PetException;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.petlife.platform.app.token.model.TokenInfo;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);


    private static final String BASE64_SECURITY_KEY = Base64.getEncoder().encodeToString("pet-key".getBytes(StandardCharsets.UTF_8));

    /**
     * 创建令牌
     *
     * @param user   user
     * @param expire 过期时间（秒)
     * @return jwt
     */
    public static TokenInfo generateJwt(Map<String, String> user, long expire) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 当前时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //添加Token过期时间
        long expMillis = nowMillis + expire * 1000;
        Date exp = new Date(expMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(BASE64_SECURITY_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的类
        JwtBuilder builder = Jwts.builder().signWith(signatureAlgorithm, signingKey);
        //设置JWT参数
        user.forEach(builder::claim);
        String token = builder
                // 发布时间
                .setIssuedAt(now)
                // token从时间什么开始生效
                .setNotBefore(now)
                // token从什么时间截止生效
                .setExpiration(exp)
                // 执行
                .compact();
        // 组装Token信息
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(token);
        tokenInfo.setExpire(expire);
        tokenInfo.setExpiration(LocalDateTimeUtil.of(exp));
        return tokenInfo;
    }

    /**
     * 解析jwt
     *
     * @param jsonWebToken            待解析token
     * @param allowedClockSkewSeconds 允许的时间差
     * @return Claims
     */
    public static Claims parseJwt(String jsonWebToken, long allowedClockSkewSeconds) {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(BASE64_SECURITY_KEY))
                    .setAllowedClockSkewSeconds(allowedClockSkewSeconds)
                    .parseClaimsJws(jsonWebToken)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            log.error("token=[{}], 过期", jsonWebToken, ex);
            //过期
            throw new PetException(CommonExceptionCode.JWT_TOKEN_EXPIRED);
        } catch (IllegalArgumentException ex) {
            log.error("token=[{}] 为空", jsonWebToken, ex);
            //token 为空
            throw new PetException(CommonExceptionCode.JWT_ILLEGAL_ARGUMENT);
        } catch (Exception e) {
            log.error("token=[{}] errCode:{}, message:{}", jsonWebToken, CommonExceptionCode.JWT_PARSER_TOKEN_FAIL, e.getMessage(), e);
            throw new PetException(CommonExceptionCode.JWT_PARSER_TOKEN_FAIL);
        }
    }

    /**
     * 简单生成 token，只需传 userId（后期可加 nickName、头像等）
     *
     * @param userId 用户ID
     * @param expire 过期时间（秒）
     * @return token 字符串
     */
    public static String generateToken(String userId, long expire) {
        Map<String, String> claims = new HashMap<>();
        claims.put("userId", userId);
        TokenInfo tokenInfo = generateJwt(claims, expire);
        return tokenInfo.getToken();
    }

}

