package com.petlife.platform.app.service;

import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.core.page.TableDataInfo;
import com.petlife.platform.common.pojo.dto.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 附近用户功能服务接口
 * 
 * @author petlife
 * @date 2025-07-28
 */
public interface INearbyUserService {
    
    /**
     * 更新用户位置信息
     * 
     * @param userId 用户ID
     * @param locationDto 位置更新信息
     * @return 更新结果
     */
    AjaxResult updateUserLocation(Long userId, LocationUpdateDto locationDto);
    
    /**
     * 获取附近用户列表（基础查询）
     * 
     * @param userId 当前用户ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param radius 搜索半径（米）
     * @return 附近用户列表
     */
    TableDataInfo getNearbyUsers(Long userId, Integer pageNum, Integer pageSize, Integer radius);
    
    /**
     * 获取附近用户列表（带筛选条件）
     * 
     * @param userId 当前用户ID
     * @param queryDto 查询条件
     * @return 附近用户列表
     */
    TableDataInfo getNearbyUsersWithFilter(Long userId, NearbyQueryDto queryDto);
    
    /**
     * 根据地址搜索获取坐标
     * 
     * @param address 地址
     * @return 坐标信息
     */
    AjaxResult searchLocationByAddress(String address);
    
    /**
     * 设置用户自定义定位
     * 
     * @param userId 用户ID
     * @param latitude 纬度
     * @param longitude 经度
     * @param address 地址描述
     * @return 设置结果
     */
    AjaxResult setCustomLocation(Long userId, BigDecimal latitude, BigDecimal longitude, String address);
    
    /**
     * 获取用户个人主页信息
     * 
     * @param userId 目标用户ID
     * @param currentUserId 当前登录用户ID
     * @return 用户个人主页信息
     */
    AjaxResult getUserProfile(Long userId, Long currentUserId);
    
    /**
     * 关注/取消关注用户
     * 
     * @param followerId 关注者ID
     * @param followedId 被关注者ID
     * @param isFollow true=关注，false=取消关注
     * @return 操作结果
     */
    AjaxResult followUser(Long followerId, Long followedId, Boolean isFollow);
    
    /**
     * 检查用户是否已关注
     * 
     * @param followerId 关注者ID
     * @param followedId 被关注者ID
     * @return 是否已关注
     */
    Boolean isUserFollowed(Long followerId, Long followedId);
    
    /**
     * 获取用户关注列表
     * 
     * @param userId 用户ID
     * @param currentUserId 当前登录用户ID（用于权限检查）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 关注列表
     */
    TableDataInfo getUserFollowList(Long userId, Long currentUserId, Integer pageNum, Integer pageSize);
    
    /**
     * 获取用户粉丝列表
     * 
     * @param userId 用户ID
     * @param currentUserId 当前登录用户ID（用于权限检查）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 粉丝列表
     */
    TableDataInfo getUserFansList(Long userId, Long currentUserId, Integer pageNum, Integer pageSize);
    
    /**
     * 获取用户动态列表
     * 
     * @param userId 用户ID
     * @param currentUserId 当前登录用户ID（用于权限检查）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 动态列表
     */
    TableDataInfo getUserPosts(Long userId, Long currentUserId, Integer pageNum, Integer pageSize);
    
    /**
     * 获取用户宠物列表
     * 
     * @param userId 用户ID
     * @return 宠物列表
     */
    AjaxResult getUserPets(Long userId);
    
    /**
     * 动态点赞/取消点赞
     * 
     * @param postId 动态ID
     * @param userId 用户ID
     * @param isLike true=点赞，false=取消点赞
     * @return 操作结果
     */
    AjaxResult likePost(Long postId, Long userId, Boolean isLike);
    
    /**
     * 动态收藏/取消收藏
     * 
     * @param postId 动态ID
     * @param userId 用户ID
     * @param isFavorite true=收藏，false=取消收藏
     * @return 操作结果
     */
    AjaxResult favoritePost(Long postId, Long userId, Boolean isFavorite);
    
    /**
     * 更新动态可见范围
     * 
     * @param postId 动态ID
     * @param userId 用户ID（权限检查）
     * @param visibility 可见范围
     * @return 操作结果
     */
    AjaxResult updatePostVisibility(Long postId, Long userId, Integer visibility);
    
    /**
     * 删除动态
     * 
     * @param postId 动态ID
     * @param userId 用户ID（权限检查）
     * @return 操作结果
     */
    AjaxResult deletePost(Long postId, Long userId);
    
    /**
     * 更新用户在线状态
     * 
     * @param userId 用户ID
     * @param deviceType 设备类型
     * @param appVersion APP版本
     * @return 更新结果
     */
    AjaxResult updateUserOnlineStatus(Long userId, String deviceType, String appVersion);
    
    /**
     * 批量设置用户离线（定时任务用）
     * 
     * @param inactiveMinutes 非活跃分钟数
     * @return 处理结果
     */
    AjaxResult batchSetUsersOffline(Integer inactiveMinutes);
    
    /**
     * 清理过期的位置缓存（定时任务用）
     * 
     * @return 清理结果
     */
    AjaxResult cleanExpiredLocationCache();
    
    /**
     * 获取热门宠物品种（用于筛选器）
     * 
     * @param limit 返回数量限制
     * @return 热门宠物品种列表
     */
    List<String> getPopularPetBreeds(Integer limit);
    
    /**
     * 根据关键词搜索宠物品种
     * 
     * @param keyword 关键词
     * @param limit 返回数量限制
     * @return 匹配的宠物品种列表
     */
    List<String> searchPetBreeds(String keyword, Integer limit);
}
