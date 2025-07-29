package com.petlife.platform.app.controller;

import com.petlife.platform.app.service.INearbyUserService;
import com.petlife.platform.common.core.controller.BaseController;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.core.page.TableDataInfo;
import com.petlife.platform.common.pojo.dto.LocationUpdateDto;
import com.petlife.platform.common.pojo.dto.NearbyQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * APP端附近用户功能控制器
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Api(tags = "APP端附近用户功能")
@RestController
@RequestMapping("/app/nearby")
public class NearbyUserController extends BaseController {
    
    private static final Logger log = LoggerFactory.getLogger(NearbyUserController.class);
    
    @Autowired
    private INearbyUserService nearbyUserService;
    
    /**
     * 更新用户位置信息
     */
    @ApiOperation("更新用户位置信息")
    @PostMapping("/location/update")
    public AjaxResult updateLocation(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @Valid @RequestBody LocationUpdateDto locationDto) {
        
        log.info("用户 {} 更新位置信息: {}", userId, locationDto);
        return nearbyUserService.updateUserLocation(userId, locationDto);
    }
    
    /**
     * 根据地址搜索坐标
     */
    @ApiOperation("根据地址搜索坐标")
    @GetMapping("/location/search")
    public AjaxResult searchLocation(
            @ApiParam(value = "地址", required = true, example = "北京市朝阳区") 
            @RequestParam("address") String address) {
        
        log.info("搜索地址坐标: {}", address);
        return nearbyUserService.searchLocationByAddress(address);
    }
    
    /**
     * 设置自定义定位
     */
    @ApiOperation("设置自定义定位")
    @PostMapping("/location/custom")
    public AjaxResult setCustomLocation(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "纬度", required = true) @RequestParam("latitude") BigDecimal latitude,
            @ApiParam(value = "经度", required = true) @RequestParam("longitude") BigDecimal longitude,
            @ApiParam(value = "地址描述") @RequestParam(value = "address", required = false) String address) {
        
        log.info("用户 {} 设置自定义定位: {}, {}, {}", userId, latitude, longitude, address);
        return nearbyUserService.setCustomLocation(userId, latitude, longitude, address);
    }
    
    /**
     * 获取附近用户列表（基础查询）
     */
    @ApiOperation("获取附近用户列表")
    @GetMapping("/users")
    public TableDataInfo getNearbyUsers(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "页码", example = "1") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", example = "20") @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @ApiParam(value = "搜索半径（米）", example = "5000") @RequestParam(value = "radius", defaultValue = "5000") Integer radius) {
        
        log.info("用户 {} 获取附近用户列表，半径: {}米", userId, radius);
        return nearbyUserService.getNearbyUsers(userId, pageNum, pageSize, radius);
    }
    
    /**
     * 获取附近用户列表（带筛选条件）
     */
    @ApiOperation("获取附近用户列表（带筛选）")
    @PostMapping("/users/filter")
    public TableDataInfo getNearbyUsersWithFilter(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @Valid @RequestBody NearbyQueryDto queryDto) {
        
        log.info("用户 {} 获取附近用户列表（带筛选）: {}", userId, queryDto);
        return nearbyUserService.getNearbyUsersWithFilter(userId, queryDto);
    }
    
    /**
     * 获取用户个人主页信息
     */
    @ApiOperation("获取用户个人主页信息")
    @GetMapping("/user/profile/{userId}")
    public AjaxResult getUserProfile(
            @ApiParam(value = "目标用户ID", required = true) @PathVariable("userId") Long userId,
            @ApiParam(value = "当前登录用户ID", required = true) @RequestParam("currentUserId") Long currentUserId) {
        
        log.info("获取用户 {} 的个人主页信息，当前用户: {}", userId, currentUserId);
        return nearbyUserService.getUserProfile(userId, currentUserId);
    }
    
    /**
     * 关注/取消关注用户
     */
    @ApiOperation("关注/取消关注用户")
    @PostMapping("/user/follow")
    public AjaxResult followUser(
            @ApiParam(value = "关注者ID", required = true) @RequestParam("followerId") Long followerId,
            @ApiParam(value = "被关注者ID", required = true) @RequestParam("followedId") Long followedId,
            @ApiParam(value = "是否关注", required = true) @RequestParam("isFollow") Boolean isFollow) {
        
        log.info("用户 {} {} 用户 {}", followerId, isFollow ? "关注" : "取消关注", followedId);
        return nearbyUserService.followUser(followerId, followedId, isFollow);
    }
    
    /**
     * 检查用户关注状态
     */
    @ApiOperation("检查用户关注状态")
    @GetMapping("/user/follow/status")
    public AjaxResult checkFollowStatus(
            @ApiParam(value = "关注者ID", required = true) @RequestParam("followerId") Long followerId,
            @ApiParam(value = "被关注者ID", required = true) @RequestParam("followedId") Long followedId) {
        
        Boolean isFollowed = nearbyUserService.isUserFollowed(followerId, followedId);
        return AjaxResult.success("查询成功", isFollowed);
    }
    
    /**
     * 获取用户关注列表
     */
    @ApiOperation("获取用户关注列表")
    @GetMapping("/user/{userId}/follows")
    public TableDataInfo getUserFollowList(
            @ApiParam(value = "用户ID", required = true) @PathVariable("userId") Long userId,
            @ApiParam(value = "当前登录用户ID", required = true) @RequestParam("currentUserId") Long currentUserId,
            @ApiParam(value = "页码", example = "1") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", example = "20") @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        
        log.info("获取用户 {} 的关注列表", userId);
        return nearbyUserService.getUserFollowList(userId, currentUserId, pageNum, pageSize);
    }
    
    /**
     * 获取用户粉丝列表
     */
    @ApiOperation("获取用户粉丝列表")
    @GetMapping("/user/{userId}/fans")
    public TableDataInfo getUserFansList(
            @ApiParam(value = "用户ID", required = true) @PathVariable("userId") Long userId,
            @ApiParam(value = "当前登录用户ID", required = true) @RequestParam("currentUserId") Long currentUserId,
            @ApiParam(value = "页码", example = "1") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", example = "20") @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        
        log.info("获取用户 {} 的粉丝列表", userId);
        return nearbyUserService.getUserFansList(userId, currentUserId, pageNum, pageSize);
    }
    
    /**
     * 获取用户动态列表
     */
    @ApiOperation("获取用户动态列表")
    @GetMapping("/user/{userId}/posts")
    public TableDataInfo getUserPosts(
            @ApiParam(value = "用户ID", required = true) @PathVariable("userId") Long userId,
            @ApiParam(value = "当前登录用户ID", required = true) @RequestParam("currentUserId") Long currentUserId,
            @ApiParam(value = "页码", example = "1") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", example = "20") @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        
        log.info("获取用户 {} 的动态列表", userId);
        return nearbyUserService.getUserPosts(userId, currentUserId, pageNum, pageSize);
    }
    
    /**
     * 获取用户宠物列表
     */
    @ApiOperation("获取用户宠物列表")
    @GetMapping("/user/{userId}/pets")
    public AjaxResult getUserPets(
            @ApiParam(value = "用户ID", required = true) @PathVariable("userId") Long userId) {
        
        log.info("获取用户 {} 的宠物列表", userId);
        return nearbyUserService.getUserPets(userId);
    }
    
    /**
     * 动态点赞/取消点赞
     */
    @ApiOperation("动态点赞/取消点赞")
    @PostMapping("/post/like")
    public AjaxResult likePost(
            @ApiParam(value = "动态ID", required = true) @RequestParam("postId") Long postId,
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "是否点赞", required = true) @RequestParam("isLike") Boolean isLike) {
        
        log.info("用户 {} {} 动态 {}", userId, isLike ? "点赞" : "取消点赞", postId);
        return nearbyUserService.likePost(postId, userId, isLike);
    }
    
    /**
     * 动态收藏/取消收藏
     */
    @ApiOperation("动态收藏/取消收藏")
    @PostMapping("/post/favorite")
    public AjaxResult favoritePost(
            @ApiParam(value = "动态ID", required = true) @RequestParam("postId") Long postId,
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "是否收藏", required = true) @RequestParam("isFavorite") Boolean isFavorite) {
        
        log.info("用户 {} {} 动态 {}", userId, isFavorite ? "收藏" : "取消收藏", postId);
        return nearbyUserService.favoritePost(postId, userId, isFavorite);
    }
    
    /**
     * 更新动态可见范围
     */
    @ApiOperation("更新动态可见范围")
    @PutMapping("/post/{postId}/visibility")
    public AjaxResult updatePostVisibility(
            @ApiParam(value = "动态ID", required = true) @PathVariable("postId") Long postId,
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "可见范围", required = true) @RequestParam("visibility") Integer visibility) {
        
        log.info("用户 {} 更新动态 {} 的可见范围为 {}", userId, postId, visibility);
        return nearbyUserService.updatePostVisibility(postId, userId, visibility);
    }
    
    /**
     * 删除动态
     */
    @ApiOperation("删除动态")
    @DeleteMapping("/post/{postId}")
    public AjaxResult deletePost(
            @ApiParam(value = "动态ID", required = true) @PathVariable("postId") Long postId,
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId) {
        
        log.info("用户 {} 删除动态 {}", userId, postId);
        return nearbyUserService.deletePost(postId, userId);
    }
    
    /**
     * 获取热门宠物品种
     */
    @ApiOperation("获取热门宠物品种")
    @GetMapping("/pet-breeds/popular")
    public AjaxResult getPopularPetBreeds(
            @ApiParam(value = "返回数量", example = "10") @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        
        List<String> breeds = nearbyUserService.getPopularPetBreeds(limit);
        return AjaxResult.success("获取热门宠物品种成功", breeds);
    }
    
    /**
     * 搜索宠物品种
     */
    @ApiOperation("搜索宠物品种")
    @GetMapping("/pet-breeds/search")
    public AjaxResult searchPetBreeds(
            @ApiParam(value = "关键词", required = true, example = "布偶") @RequestParam("keyword") String keyword,
            @ApiParam(value = "返回数量", example = "10") @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        
        List<String> breeds = nearbyUserService.searchPetBreeds(keyword, limit);
        return AjaxResult.success("搜索宠物品种成功", breeds);
    }
    
    /**
     * 更新用户在线状态
     */
    @ApiOperation("更新用户在线状态")
    @PostMapping("/user/online-status")
    public AjaxResult updateOnlineStatus(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "设备类型") @RequestParam(value = "deviceType", required = false) String deviceType,
            @ApiParam(value = "APP版本") @RequestParam(value = "appVersion", required = false) String appVersion) {
        
        return nearbyUserService.updateUserOnlineStatus(userId, deviceType, appVersion);
    }
}
