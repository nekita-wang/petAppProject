package com.petlife.platform.app.mapper;

import com.petlife.platform.common.pojo.entity.StatusInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatusInfoMapper {
    @Insert("INSERT INTO status_info (UserID, ChangeType, OldStatus, NewStatus, ChangeTime, OperatorType, Reason, Proof) " +
            "VALUES (#{userId}, #{changeType}, #{oldStatus}, #{newStatus}, #{changeTime}, #{operatorType}, #{reason}, #{proof})")
    int insert(StatusInfo statusInfo);
} 