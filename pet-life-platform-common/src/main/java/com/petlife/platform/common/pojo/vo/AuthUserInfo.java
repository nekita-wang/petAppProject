package com.petlife.platform.common.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 认证成功返回用户相关信息
 */
@Data
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

    // 是否是新用户
    private Boolean newUser;
}
