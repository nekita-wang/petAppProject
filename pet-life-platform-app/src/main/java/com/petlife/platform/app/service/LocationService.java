package com.petlife.platform.app.service;

import com.petlife.platform.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 位置服务类 - 处理GPS定位、距离计算等功能
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Service
public class LocationService {

    @Autowired
    private MapService mapService;

    @Autowired
    private SmartSearchService smartSearchService;

    /**
     * 计算两点之间的距离（使用Haversine公式）
     * 
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     * @return 距离（米）
     */
    public BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            return BigDecimal.ZERO;
        }

        // 地球半径（米）
        final double EARTH_RADIUS = 6371000;

        double lat1Rad = Math.toRadians(lat1.doubleValue());
        double lon1Rad = Math.toRadians(lon1.doubleValue());
        double lat2Rad = Math.toRadians(lat2.doubleValue());
        double lon2Rad = Math.toRadians(lon2.doubleValue());

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 格式化距离显示
     * 
     * @param distanceInMeters 距离（米）
     * @return 格式化的距离字符串
     */
    public String formatDistance(BigDecimal distanceInMeters) {
        if (distanceInMeters == null || distanceInMeters.compareTo(BigDecimal.ZERO) <= 0) {
            return "未知距离";
        }

        double distance = distanceInMeters.doubleValue();

        if (distance < 1000) {
            return String.format("%.0fm", distance);
        } else if (distance < 10000) {
            return String.format("%.1fkm", distance / 1000);
        } else {
            return String.format("%.0fkm", distance / 1000);
        }
    }

    /**
     * 根据距离类型获取距离范围
     * 
     * @param distanceType 距离类型：1=0-1km, 2=1-3km, 3=3-5km, 4=5km以上
     * @return 距离范围数组 [最小距离, 最大距离]（米）
     */
    public int[] getDistanceRange(Integer distanceType) {
        if (distanceType == null) {
            return new int[] { 0, Integer.MAX_VALUE };
        }

        switch (distanceType) {
            case 1:
                return new int[] { 0, 1000 }; // 0-1km
            case 2:
                return new int[] { 1000, 3000 }; // 1-3km
            case 3:
                return new int[] { 3000, 5000 }; // 3-5km
            case 4:
                return new int[] { 5000, Integer.MAX_VALUE }; // 5km以上
            default:
                return new int[] { 0, Integer.MAX_VALUE }; // 不限制
        }
    }

    /**
     * 验证坐标是否有效
     * 
     * @param latitude  纬度
     * @param longitude 经度
     * @return 是否有效
     */
    public boolean isValidCoordinate(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null || longitude == null) {
            return false;
        }

        // 纬度范围：-90 到 90
        // 经度范围：-180 到 180
        return latitude.compareTo(new BigDecimal("-90")) >= 0 &&
                latitude.compareTo(new BigDecimal("90")) <= 0 &&
                longitude.compareTo(new BigDecimal("-180")) >= 0 &&
                longitude.compareTo(new BigDecimal("180")) <= 0;
    }

    /**
     * 通过地址获取坐标（集成天地图API）
     * 
     * @param address 地址
     * @return 坐标信息
     */
    public AjaxResult getCoordinatesByAddress(String address) {
        try {
            Map<String, Object> result = mapService.geocoding(address);

            if (result != null) {
                // 验证返回的坐标
                String lonStr = (String) result.get("longitude");
                String latStr = (String) result.get("latitude");

                if (lonStr != null && latStr != null) {
                    BigDecimal longitude = new BigDecimal(lonStr);
                    BigDecimal latitude = new BigDecimal(latStr);

                    if (isValidCoordinate(latitude, longitude)) {
                        Map<String, Object> locationData = new HashMap<>();
                        locationData.put("address", address);
                        locationData.put("longitude", longitude);
                        locationData.put("latitude", latitude);
                        locationData.put("provider", mapService.getCurrentProvider());
                        locationData.put("accuracy", "≤10米");

                        return AjaxResult.success("地址解析成功", locationData);
                    }
                }
            }

            return AjaxResult.error("地址解析失败，请输入有效地址");

        } catch (Exception e) {
            return AjaxResult.error("地址解析异常：" + e.getMessage());
        }
    }

    /**
     * 通过坐标获取地址（集成天地图API）
     * 
     * @param longitude 经度
     * @param latitude  纬度
     * @return 地址信息
     */
    public AjaxResult getAddressByCoordinates(BigDecimal longitude, BigDecimal latitude) {
        try {
            if (!isValidCoordinate(latitude, longitude)) {
                return AjaxResult.error("坐标无效");
            }

            Map<String, Object> result = mapService.reverseGeocoding(longitude, latitude);

            if (result != null) {
                Map<String, Object> addressData = new HashMap<>();
                addressData.put("longitude", longitude);
                addressData.put("latitude", latitude);
                addressData.put("address", result.get("address"));
                addressData.put("provider", mapService.getCurrentProvider());

                // 如果有详细地址信息，也包含进去
                if (result.containsKey("province")) {
                    addressData.put("province", result.get("province"));
                }
                if (result.containsKey("city")) {
                    addressData.put("city", result.get("city"));
                }
                if (result.containsKey("district")) {
                    addressData.put("district", result.get("district"));
                }

                return AjaxResult.success("坐标解析成功", addressData);
            }

            return AjaxResult.error("坐标解析失败");

        } catch (Exception e) {
            return AjaxResult.error("坐标解析异常：" + e.getMessage());
        }
    }

    /**
     * 计算用户移动距离
     * 
     * @param oldLat 原纬度
     * @param oldLon 原经度
     * @param newLat 新纬度
     * @param newLon 新经度
     * @return 移动距离（米）
     */
    public BigDecimal calculateMovedDistance(BigDecimal oldLat, BigDecimal oldLon,
            BigDecimal newLat, BigDecimal newLon) {
        return calculateDistance(oldLat, oldLon, newLat, newLon);
    }

    /**
     * 判断是否需要更新位置（移动距离超过阈值）
     * 
     * @param movedDistance 移动距离（米）
     * @param threshold     阈值（米），默认10米
     * @return 是否需要更新
     */
    public boolean shouldUpdateLocation(BigDecimal movedDistance, int threshold) {
        if (movedDistance == null) {
            return true; // 首次定位
        }
        return movedDistance.compareTo(new BigDecimal(threshold)) > 0;
    }

    /**
     * 获取地址搜索建议
     * 基于历史搜索和热门地点提供智能建议
     *
     * @param keyword 搜索关键词
     * @param limit   建议数量限制
     * @return 搜索建议列表
     */
    public List<String> getSearchSuggestions(String keyword, Integer limit) {
        return smartSearchService.getSmartSuggestions(keyword, limit);
    }

    /**
     * 记录搜索历史
     *
     * @param keyword       搜索关键词
     * @param resultAddress 搜索结果地址
     * @param successful    搜索是否成功
     */
    public void recordSearchHistory(String keyword, String resultAddress, boolean successful) {
        smartSearchService.recordSearchHistory(keyword, resultAddress, successful);
    }

    /**
     * 记录搜索历史（兼容旧接口）
     *
     * @param keyword    搜索关键词
     * @param successful 搜索是否成功
     */
    public void recordSearchHistory(String keyword, boolean successful) {
        smartSearchService.recordSearchHistory(keyword, null, successful);
    }

    /**
     * 评估搜索精度
     * 根据地址特征动态评估定位精度
     *
     * @param address 搜索地址
     * @return 精度描述
     */
    public String evaluateSearchAccuracy(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "未知精度";
        }

        String addr = address.trim();

        // 具体门牌号地址 - 高精度
        if (addr.matches(".*\\d+号.*") || addr.matches(".*\\d+\\-\\d+.*") || addr.matches(".*\\d+栋.*")) {
            return "≤10米";
        }

        // 具体建筑物/商场/学校等 - 中高精度
        if (addr.contains("大厦") || addr.contains("广场") || addr.contains("商场") ||
                addr.contains("医院") || addr.contains("学校") || addr.contains("大学") ||
                addr.contains("酒店") || addr.contains("银行") || addr.contains("超市") ||
                addr.contains("公园") || addr.contains("体育馆") || addr.contains("机场") ||
                addr.contains("火车站") || addr.contains("地铁站")) {
            return "≤50米";
        }

        // 具体街道地址 - 中等精度
        if (addr.contains("街") || addr.contains("路") || addr.contains("巷") ||
                addr.contains("胡同") || addr.contains("弄")) {
            return "≤100米";
        }

        // 区域性地标 - 低精度
        if (addr.matches(".*[市县区].*") && (addr.contains("区") || addr.contains("县") || addr.contains("市"))) {
            if (addr.length() <= 6 && !addr.matches(".*\\d+.*")) {
                return "≤1000米";
            }
            return "≤200米";
        }

        // 知名地标但位置模糊 - 中低精度
        if (addr.length() <= 8 && !addr.matches(".*\\d+.*")) {
            return "≤500米";
        }

        // 默认中等精度
        return "≤100米";
    }
}
