package com.petlife.platform.app.token.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: xusu
 * @Description: JwtUtil工具生成token返回实体
 * @Date: 2025/06/28
 */
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * token
     */
    private String token;
    /**
     * 有效时间：单位：秒
     */
    private Long expire;
    /**
     * 到期时间
     */
    private LocalDateTime expiration;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
