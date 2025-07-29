package com.petlife.platform.common.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户位置信息实体类
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {
    
    /**
     * 位置记录ID
     */
    private Long locationId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 纬度，精度到小数点后8位
     */
    private BigDecimal latitude;
    
    /**
     * 经度，精度到小数点后8位
     */
    private BigDecimal longitude;
    
    /**
     * 定位精度（米）
     */
    private BigDecimal accuracy;
    
    /**
     * 定位类型（1=GPS，2=基站，3=WiFi，4=混合）
     */
    private Byte locationType;
    
    /**
     * 地址描述（可选）
     */
    private String address;
    
    /**
     * 位置更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    /**
     * 首次定位时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 是否为当前有效位置（0=否，1=是）
     */
    private Byte isActive;
    
    /**
     * 构造函数 - 用于创建新的位置记录
     */
    public UserLocation(Long userId, BigDecimal latitude, BigDecimal longitude, 
                       BigDecimal accuracy, Byte locationType) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.locationType = locationType;
        this.isActive = (byte) 1;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
