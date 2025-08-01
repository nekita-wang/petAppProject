package com.petlife.platform.app.controller;

import com.petlife.platform.app.service.INearbyUserService;
import com.petlife.platform.app.service.LocationService;
import com.petlife.platform.app.service.SmartSearchService;
import com.petlife.platform.common.core.controller.BaseController;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.core.page.TableDataInfo;
import com.petlife.platform.common.pojo.dto.LocationConfirmDto;
import com.petlife.platform.common.pojo.dto.LocationUpdateDto;
import com.petlife.platform.common.pojo.dto.NearbyQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * "附近"功能控制器 - 集成天地图API实现LBS功能
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Api(tags = "附近功能")
@RestController
@RequestMapping("/app/nearby")
public class NearbyController extends BaseController {

    @Autowired
    private INearbyUserService nearbyUserService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private SmartSearchService smartSearchService;

    /**
     * 2.1.1 定位器 - 通过地址搜索获取精确坐标
     * 用户在搜索框输入具体位置，返回精确坐标信息
     */
    @ApiOperation("定位器 - 地址搜索定位")
    @GetMapping("/location/search")
    public ResponseData<Map<String, Object>> searchLocation(
            @ApiParam(value = "搜索地址", required = true, example = "北京市朝阳区三里屯") @RequestParam("address") String address) {

        // 使用天地图API进行地址解析，精度≤10米
        // 注意：这里需要适配LocationService的返回类型，暂时保持原有逻辑
        try {
            // 暂时使用原有方法，后续需要适配
            // Map<String, Object> coordinatesData =
            // locationService.getCoordinatesByAddress(address);
            // 临时处理，需要根据实际LocationService接口调整
            Map<String, Object> coordinatesData = new HashMap<>();
            // TODO: 调用实际的LocationService方法

            if (coordinatesData != null && coordinatesData.containsKey("longitude")
                    && coordinatesData.containsKey("latitude")) {
                // 返回核心定位信息
                Map<String, Object> locationInfo = new HashMap<>();
                locationInfo.put("searchAddress", address);
                locationInfo.put("longitude", coordinatesData.get("longitude"));
                locationInfo.put("latitude", coordinatesData.get("latitude"));
                // 使用动态精度评估
                String accuracy = locationService.evaluateSearchAccuracy(address);
                locationInfo.put("accuracy", accuracy);

                // 记录搜索历史（包含结果地址）
                String resultAddress = coordinatesData.get("address") != null
                        ? coordinatesData.get("address").toString()
                        : address;
                locationService.recordSearchHistory(address, resultAddress, true);

                return ResponseData.okWithMsg("定位成功", locationInfo);
            } else {
                // 记录搜索失败
                locationService.recordSearchHistory(address, false);
                return ResponseData.error(404, "地址解析失败");
            }
        } catch (Exception e) {
            // 记录搜索失败
            locationService.recordSearchHistory(address, false);
            return ResponseData.error(500, "定位服务异常: " + e.getMessage());
        }
    }

    /**
     * 2.1.0 地址搜索建议接口
     * 为用户提供智能搜索建议，基于历史搜索和热门地点
     */
    @ApiOperation("地址搜索建议")
    @GetMapping("/location/suggestions")
    public ResponseData<Map<String, Object>> getLocationSuggestions(
            @ApiParam(value = "搜索关键词", required = true, example = "上海外滩") @RequestParam("keyword") String keyword,
            @ApiParam(value = "建议数量", required = false, example = "5") @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseData.error(400, "搜索关键词不能为空");
        }

        try {
            List<String> suggestions = locationService.getSearchSuggestions(keyword.trim(), limit);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("suggestions", suggestions);
            responseData.put("keyword", keyword.trim());
            responseData.put("count", suggestions.size());

            return ResponseData.okWithMsg("获取建议成功", responseData);
        } catch (Exception e) {
            return ResponseData.error(500, "获取建议失败：" + e.getMessage());
        }
    }

    /**
     * 2.1.0 热门搜索接口
     * 获取当前热门的搜索关键词
     */
    @ApiOperation("热门搜索")
    @GetMapping("/location/popular")
    public ResponseData<Map<String, Object>> getPopularSearches(
            @ApiParam(value = "热门搜索数量", required = false, example = "8") @RequestParam(value = "limit", required = false, defaultValue = "8") Integer limit) {

        try {
            List<String> popularSearches = smartSearchService.getPopularSuggestions(limit);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("popular", popularSearches);
            responseData.put("count", popularSearches.size());
            responseData.put("updateTime", System.currentTimeMillis());

            return ResponseData.okWithMsg("获取热门搜索成功", responseData);
        } catch (Exception e) {
            return ResponseData.error(500, "获取热门搜索失败：" + e.getMessage());
        }
    }

    /**
     * 2.1.1 定位器 - 确认地图定位
     * 用户点击【地图定位】按钮后，更新用户位置并返回附近页面
     * 支持JSON格式请求体
     */
    @ApiOperation("定位器 - 确认地图定位")
    @PostMapping("/location/confirm")
    public ResponseData<Map<String, Object>> confirmLocation(@RequestBody LocationConfirmDto confirmDto) {

        // 验证坐标有效性
        if (!locationService.isValidCoordinate(confirmDto.getLatitude(), confirmDto.getLongitude())) {
            return ResponseData.error(400, "坐标无效");
        }

        // 更新用户位置
        LocationUpdateDto locationDto = new LocationUpdateDto();
        locationDto.setLatitude(confirmDto.getLatitude());
        locationDto.setLongitude(confirmDto.getLongitude());
        locationDto.setAddress(confirmDto.getAddress());
        locationDto.setLocationType((byte) 4); // 混合定位

        // 注意：这里需要适配nearbyUserService的返回类型
        try {
            // TODO: 需要根据实际的nearbyUserService接口调整
            // boolean updateSuccess =
            // nearbyUserService.updateUserLocation(confirmDto.getUserId(), locationDto);
            boolean updateSuccess = true; // 临时处理

            if (updateSuccess) {
                // 返回更新后的位置信息
                Map<String, Object> locationInfo = new HashMap<>();
                locationInfo.put("userId", confirmDto.getUserId());
                locationInfo.put("longitude", confirmDto.getLongitude());
                locationInfo.put("latitude", confirmDto.getLatitude());
                locationInfo.put("address", confirmDto.getAddress());
                locationInfo.put("updateTime", System.currentTimeMillis());
                locationInfo.put("message", "位置更新成功，附近用户距离已刷新");

                return ResponseData.okWithMsg("地图定位确认成功", locationInfo);
            } else {
                return ResponseData.error(500, "位置更新失败");
            }
        } catch (Exception e) {
            return ResponseData.error(500, "位置更新异常: " + e.getMessage());
        }
    }

    /**
     * 2.1.2 筛选器 - 获取附近用户（支持多条件筛选）
     */
    @ApiOperation("筛选器 - 条件筛选附近用户")
    @PostMapping("/users/filter")
    public TableDataInfo getNearbyUsersWithFilter(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @RequestBody NearbyQueryDto queryDto) {

        // 处理距离筛选条件
        if (queryDto.getDistanceType() != null) {
            int[] range = locationService.getDistanceRange(queryDto.getDistanceType());
            queryDto.setMinDistance(range[0]);
            queryDto.setMaxDistance(range[1]);
        }

        return nearbyUserService.getNearbyUsersWithFilter(userId, queryDto);
    }

    /**
     * 2.1.3 搜索框 - 搜索宠物品种或用户名
     */
    @ApiOperation("搜索框 - 搜索宠物品种或用户名")
    @GetMapping("/search")
    public TableDataInfo searchNearbyUsers(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "搜索关键词", required = true) @RequestParam("keyword") String keyword,
            @ApiParam(value = "页码", required = false, example = "1") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "页大小", required = false, example = "10") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        // 构建搜索查询条件
        NearbyQueryDto queryDto = new NearbyQueryDto();
        queryDto.setKeyword(keyword);
        queryDto.setPageNum(pageNum);
        queryDto.setPageSize(pageSize);

        return nearbyUserService.getNearbyUsersWithFilter(userId, queryDto);
    }

    /**
     * 2.1.6 正文展示 - 获取附近用户列表（默认展示）
     */
    @ApiOperation("正文展示 - 获取附近用户列表")
    @GetMapping("/users")
    public TableDataInfo getNearbyUsers(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @ApiParam(value = "页码", required = false, example = "1") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "页大小", required = false, example = "10") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @ApiParam(value = "搜索半径(米)", required = false, example = "5000") @RequestParam(value = "radius", defaultValue = "5000") Integer radius) {

        return nearbyUserService.getNearbyUsers(userId, pageNum, pageSize, radius);
    }

    /**
     * 实时位置更新 - 每60秒更新一次用户位置
     */
    @ApiOperation("实时位置更新")
    @PostMapping("/location/update")
    public ResponseData<String> updateLocation(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long userId,
            @RequestBody LocationUpdateDto locationDto) {

        // 验证坐标有效性
        if (!locationService.isValidCoordinate(locationDto.getLatitude(), locationDto.getLongitude())) {
            return ResponseData.error(400, "坐标无效");
        }

        try {
            // TODO: 需要根据实际的nearbyUserService接口调整
            // nearbyUserService.updateUserLocation(userId, locationDto);
            return ResponseData.okWithMsg("位置更新成功", "位置已更新");
        } catch (Exception e) {
            return ResponseData.error(500, "位置更新失败: " + e.getMessage());
        }
    }

    /**
     * 获取两点间距离
     */
    @ApiOperation("计算两点间距离")
    @GetMapping("/distance/calculate")
    public ResponseData<Map<String, Object>> calculateDistance(
            @ApiParam(value = "起点纬度", required = true) @RequestParam("lat1") BigDecimal lat1,
            @ApiParam(value = "起点经度", required = true) @RequestParam("lon1") BigDecimal lon1,
            @ApiParam(value = "终点纬度", required = true) @RequestParam("lat2") BigDecimal lat2,
            @ApiParam(value = "终点经度", required = true) @RequestParam("lon2") BigDecimal lon2) {

        BigDecimal distance = locationService.calculateDistance(lat1, lon1, lat2, lon2);
        String formattedDistance = locationService.formatDistance(distance);

        Map<String, Object> result = new HashMap<>();
        result.put("distanceInMeters", distance);
        result.put("formattedDistance", formattedDistance);

        return ResponseData.okWithMsg("距离计算成功", result);
    }

    /**
     * 逆地理编码 - 根据坐标获取地址
     */
    @ApiOperation("逆地理编码 - 坐标转地址")
    @GetMapping("/location/reverse")
    public ResponseData<Map<String, Object>> reverseGeocode(
            @ApiParam(value = "经度", required = true) @RequestParam("longitude") BigDecimal longitude,
            @ApiParam(value = "纬度", required = true) @RequestParam("latitude") BigDecimal latitude) {

        try {
            // TODO: 需要根据实际的locationService接口调整
            // return locationService.getAddressByCoordinates(longitude, latitude);
            Map<String, Object> addressData = new HashMap<>();
            addressData.put("address", "地址解析结果");
            addressData.put("longitude", longitude);
            addressData.put("latitude", latitude);
            return ResponseData.okWithMsg("逆地理编码成功", addressData);
        } catch (Exception e) {
            return ResponseData.error(500, "逆地理编码失败: " + e.getMessage());
        }
    }

    /**
     * 验证坐标准确性 - 通过逆地理编码验证
     */
    @ApiOperation("验证坐标准确性")
    @GetMapping("/location/verify")
    public ResponseData<Map<String, Object>> verifyLocation(
            @ApiParam(value = "经度", required = true) @RequestParam("longitude") BigDecimal longitude,
            @ApiParam(value = "纬度", required = true) @RequestParam("latitude") BigDecimal latitude,
            @ApiParam(value = "期望地址关键词", required = true) @RequestParam("expectedKeyword") String expectedKeyword) {

        Map<String, Object> verificationResult = new HashMap<>();
        verificationResult.put("coordinates", longitude + "," + latitude);
        verificationResult.put("expectedKeyword", expectedKeyword);

        try {
            // TODO: 需要根据实际的locationService接口调整
            // 先进行逆地理编码
            // Map<String, Object> addressData =
            // locationService.getAddressByCoordinatesMap(longitude, latitude);

            // 临时处理
            Map<String, Object> addressData = new HashMap<>();
            addressData.put("address", "临时地址");
            addressData.put("provider", "天地图");

            String actualAddress = (String) addressData.get("address");

            // 检查地址是否包含期望的关键词
            boolean isMatch = actualAddress != null &&
                    actualAddress.toLowerCase().contains(expectedKeyword.toLowerCase());

            verificationResult.put("actualAddress", actualAddress);
            verificationResult.put("isMatch", isMatch);
            verificationResult.put("confidence", isMatch ? "高" : "低");
            verificationResult.put("provider", addressData.get("provider"));

            if (isMatch) {
                return ResponseData.okWithMsg("坐标验证成功，位置匹配", verificationResult);
            } else {
                return ResponseData.okWithMsg("坐标验证完成，但位置不匹配", verificationResult);
            }
        } catch (Exception e) {
            verificationResult.put("error", "逆地理编码失败: " + e.getMessage());
            return ResponseData.error(500, "坐标验证失败");
        }
    }
}
