package com.petlife.platform.common.pojo.entity;

import lombok.Data;
import java.util.Date;

/**
 * 状态信息
 */
@Data
public class StatusInfo {
    private Long statusId;
    private Long userId;
    private Integer changeType;
    private Integer oldStatus;
    private Integer newStatus;
    private Date changeTime;
    private Integer operatorType;
    private String reason;
    private String proof;
} 