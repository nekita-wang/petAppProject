package com.petlife.platform.common.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户在线状态实体类
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOnlineStatus {
    
    /**
     * 状态记录ID
     */
    private Long statusId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 是否在线（0=离线，1=在线）
     */
    private Byte isOnline;
    
    /**
     * 最后活跃时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastActiveTime;
    
    /**
     * 设备类型（iOS/Android）
     */
    private String deviceType;
    
    /**
     * APP版本号
     */
    private String appVersion;
    
    /**
     * 位置权限状态（0=未授权，1=已授权）
     */
    private Byte locationPermission;
    
    /**
     * 后台定位权限（0=未授权，1=已授权）
     */
    private Byte backgroundLocation;
    
    /**
     * 状态更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    /**
     * 构造函数 - 用于创建新的在线状态记录
     */
    public UserOnlineStatus(Long userId, String deviceType, String appVersion) {
        this.userId = userId;
        this.deviceType = deviceType;
        this.appVersion = appVersion;
        this.isOnline = (byte) 1;
        this.locationPermission = (byte) 0;
        this.backgroundLocation = (byte) 0;
        this.lastActiveTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
