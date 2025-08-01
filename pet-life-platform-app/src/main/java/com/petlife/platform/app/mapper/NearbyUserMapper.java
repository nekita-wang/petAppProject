package com.petlife.platform.app.mapper;

import com.petlife.platform.common.pojo.dto.NearbyQueryDto;
import com.petlife.platform.common.pojo.dto.NearbyUserDto;
import com.petlife.platform.common.pojo.entity.UserLocation;
import com.petlife.platform.common.pojo.entity.UserOnlineStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 附近用户功能Mapper接口
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Mapper
public interface NearbyUserMapper {

    /**
     * 更新用户位置信息
     */
    int updateUserLocation(UserLocation location);

    /**
     * 将用户的其他位置记录设为非活跃
     */
    int deactivateUserLocations(@Param("userId") Long userId);

    /**
     * 插入用户位置信息
     */
    int insertUserLocation(UserLocation location);

    /**
     * 获取用户当前位置
     */
    UserLocation getUserCurrentLocation(@Param("userId") Long userId);

    /**
     * 更新用户在线状态
     */
    int updateUserOnlineStatus(UserOnlineStatus status);

    /**
     * 插入用户在线状态
     */
    int insertUserOnlineStatus(UserOnlineStatus status);

    /**
     * 获取用户在线状态
     */
    UserOnlineStatus getUserOnlineStatus(@Param("userId") Long userId);

    /**
     * 查询附近用户（基础查询，不包含筛选）
     */
    List<NearbyUserDto> findNearbyUsers(@Param("userId") Long userId,
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("radius") Integer radius,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    /**
     * 查询附近用户（包含筛选条件）
     */
    List<NearbyUserDto> findNearbyUsersWithFilter(@Param("userId") Long userId,
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("query") NearbyQueryDto query,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    /**
     * 统计附近用户总数
     */
    Long countNearbyUsers(@Param("userId") Long userId,
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("radius") Integer radius);

    /**
     * 统计附近用户总数（包含筛选条件）
     */
    Long countNearbyUsersWithFilter(@Param("userId") Long userId,
            @Param("latitude") BigDecimal latitude,
            @Param("longitude") BigDecimal longitude,
            @Param("query") NearbyQueryDto query);

    /**
     * 获取用户的宠物简要信息
     */
    List<NearbyUserDto.PetBriefInfo> getUserPetBriefInfo(@Param("userId") Long userId);

    /**
     * 批量获取多个用户的宠物简要信息
     */
    List<NearbyUserDto.PetBriefInfo> getBatchUserPetBriefInfo(@Param("userIds") List<Long> userIds);

    /**
     * 清理过期的位置缓存
     */
    int cleanExpiredLocationCache();

    /**
     * 更新用户最后活跃时间
     */
    int updateUserLastActiveTime(@Param("userId") Long userId);

    /**
     * 批量设置用户离线状态
     */
    int batchSetUsersOffline(@Param("userIds") List<Long> userIds);

    /**
     * 记录位置更新日志
     */
    int insertLocationUpdateLog(@Param("userId") Long userId,
            @Param("oldLatitude") BigDecimal oldLatitude,
            @Param("oldLongitude") BigDecimal oldLongitude,
            @Param("newLatitude") BigDecimal newLatitude,
            @Param("newLongitude") BigDecimal newLongitude,
            @Param("distanceMoved") BigDecimal distanceMoved,
            @Param("updateSource") String updateSource);
}
