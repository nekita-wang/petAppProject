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
    // 过期时间（秒）
    private long expire;
    //是否填写宠物信息
    private Boolean needPetInfo;
}
