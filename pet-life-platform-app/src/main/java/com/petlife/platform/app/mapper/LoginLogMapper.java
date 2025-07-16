package com.petlife.platform.app.mapper;

import com.petlife.platform.common.pojo.entity.LoginLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper {
    @Insert("INSERT INTO login_log (UserID, LoginMethod, LoginTime, LoginIP, Location, DeviceModel, DefaultLocation, LoginResult, FailCount, ErrorReason) " +
            "VALUES (#{userId}, #{loginMethod}, #{loginTime}, #{loginIP}, #{location}, #{deviceModel}, #{defaultLocation}, #{loginResult}, #{failCount}, #{errorReason})")
    int insert(LoginLog log);
} 