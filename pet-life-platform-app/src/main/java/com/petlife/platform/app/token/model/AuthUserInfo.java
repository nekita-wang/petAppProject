package com.petlife.platform.app.token.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 认证成功返回用户相关信息
 */
public class AuthUserInfo implements Serializable {
    private static final long serialVersionUID = -1L;
    // 对应登录身份的id
    private Long userId;
    // 令牌
    private String token;
    // 刷新令牌
    private String refreshToken;
    // 过期时间（秒）
    private long expire;
    // 到期时间
    private LocalDateTime expiration;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
