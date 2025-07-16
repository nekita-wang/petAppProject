package com.petlife.platform.common.pojo.entity;

import lombok.Data;
import java.util.Date;

/**
 * 登录日志
 */
@Data
public class LoginLog {
    private Long loginId;
    private Long userId;
    private Integer loginMethod;
    private Date loginTime;
    private String loginIP;
    private String location;
    private String deviceModel;
    private String defaultLocation;
    private Integer loginResult;
    private Integer failCount;
    private String errorReason;
} 