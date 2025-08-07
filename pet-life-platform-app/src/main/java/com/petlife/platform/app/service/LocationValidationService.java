package com.petlife.platform.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 位置验证和精度提升服务
 * 
 * @author petlife
 * @date 2025-08-07
 */
@Service
public class LocationValidationService {

    private static final Logger log = LoggerFactory.getLogger(LocationValidationService.class);

    // 知名地标的准确坐标数据库
    private static final Map<String, LocationInfo> LANDMARK_COORDINATES = new HashMap<>();

    static {
        // 北京地标
        LANDMARK_COORDINATES.put("故宫", new LocationInfo(new BigDecimal("116.397128"), new BigDecimal("39.916527"), "北京市东城区景山前街4号"));
        LANDMARK_COORDINATES.put("天安门", new LocationInfo(new BigDecimal("116.397477"), new BigDecimal("39.909309"), "北京市东城区天安门广场"));
        LANDMARK_COORDINATES.put("天安门广场", new LocationInfo(new BigDecimal("116.397477"), new BigDecimal("39.909309"), "北京市东城区天安门广场"));
        LANDMARK_COORDINATES.put("北京大学", new LocationInfo(new BigDecimal("116.310316"), new BigDecimal("39.992640"), "北京市海淀区颐和园路5号"));
        LANDMARK_COORDINATES.put("清华大学", new LocationInfo(new BigDecimal("116.326734"), new BigDecimal("40.003078"), "北京市海淀区清华园1号"));
        
        // 上海地标
        LANDMARK_COORDINATES.put("外滩", new LocationInfo(new BigDecimal("121.490317"), new BigDecimal("31.245417"), "上海市黄浦区中山东一路"));
        LANDMARK_COORDINATES.put("上海外滩", new LocationInfo(new BigDecimal("121.490317"), new BigDecimal("31.245417"), "上海市黄浦区中山东一路"));
        LANDMARK_COORDINATES.put("东方明珠", new LocationInfo(new BigDecimal("121.506377"), new BigDecimal("31.245105"), "上海市浦东新区世纪大道1号"));
        
        // 厦门地标
        LANDMARK_COORDINATES.put("厦门大学", new LocationInfo(new BigDecimal("118.096963"), new BigDecimal("24.435484"), "福建省厦门市思明区思明南路422号"));
        LANDMARK_COORDINATES.put("鼓浪屿", new LocationInfo(new BigDecimal("118.063663"), new BigDecimal("24.447604"), "福建省厦门市思明区鼓浪屿"));
        
        // 深圳地标
        LANDMARK_COORDINATES.put("深圳湾公园", new LocationInfo(new BigDecimal("113.969344"), new BigDecimal("22.485411"), "广东省深圳市南山区深圳湾公园"));
        LANDMARK_COORDINATES.put("平安金融中心", new LocationInfo(new BigDecimal("114.109497"), new BigDecimal("22.532431"), "广东省深圳市福田区益田路5033号"));
        
        // 广州地标
        LANDMARK_COORDINATES.put("广州塔", new LocationInfo(new BigDecimal("113.324520"), new BigDecimal("23.109468"), "广东省广州市海珠区阅江西路222号"));
        
        // 杭州地标
        LANDMARK_COORDINATES.put("西湖", new LocationInfo(new BigDecimal("120.148792"), new BigDecimal("30.252491"), "浙江省杭州市西湖区西湖"));
        LANDMARK_COORDINATES.put("杭州西湖", new LocationInfo(new BigDecimal("120.148792"), new BigDecimal("30.252491"), "浙江省杭州市西湖区西湖"));
    }

    /**
     * 验证坐标准确性
     * 
     * @param address 地址
     * @param longitude 经度
     * @param latitude 纬度
     * @return 验证结果
     */
    public LocationValidationResult validateLocation(String address, BigDecimal longitude, BigDecimal latitude) {
        LocationValidationResult result = new LocationValidationResult();
        result.setOriginalAddress(address);
        result.setOriginalLongitude(longitude);
        result.setOriginalLatitude(latitude);

        // 1. 基本坐标范围检查
        if (!isValidCoordinateRange(longitude, latitude)) {
            result.setValid(false);
            result.setErrorMessage("坐标超出有效范围");
            return result;
        }

        // 2. 检查是否为知名地标
        LocationInfo knownLocation = findKnownLocation(address);
        if (knownLocation != null) {
            BigDecimal distance = calculateDistance(latitude, longitude, 
                knownLocation.getLatitude(), knownLocation.getLongitude());
            
            result.setKnownLandmark(true);
            result.setExpectedLongitude(knownLocation.getLongitude());
            result.setExpectedLatitude(knownLocation.getLatitude());
            result.setExpectedAddress(knownLocation.getAddress());
            result.setDistanceFromExpected(distance);
            
            // 如果距离超过10公里，认为坐标可能有误
            if (distance.compareTo(new BigDecimal("10000")) > 0) {
                result.setValid(false);
                result.setErrorMessage(String.format("坐标与预期位置相距%.2f公里，可能有误", 
                    distance.divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP).doubleValue()));
                result.setSuggestedLongitude(knownLocation.getLongitude());
                result.setSuggestedLatitude(knownLocation.getLatitude());
            } else {
                result.setValid(true);
                result.setAccuracyLevel("高精度");
            }
        } else {
            // 3. 基于地址特征进行区域验证
            String region = extractRegion(address);
            if (region != null && !isCoordinateInRegion(longitude, latitude, region)) {
                result.setValid(false);
                result.setErrorMessage("坐标与地址所在区域不符");
            } else {
                result.setValid(true);
                result.setAccuracyLevel("中等精度");
            }
        }

        return result;
    }

    /**
     * 提升位置精确度
     * 
     * @param address 地址
     * @param longitude 原始经度
     * @param latitude 原始纬度
     * @return 优化后的位置信息
     */
    public LocationInfo enhanceLocationAccuracy(String address, BigDecimal longitude, BigDecimal latitude) {
        // 1. 检查是否为知名地标
        LocationInfo knownLocation = findKnownLocation(address);
        if (knownLocation != null) {
            log.info("使用预设的高精度坐标: {} -> {},{}", address, 
                knownLocation.getLongitude(), knownLocation.getLatitude());
            return knownLocation;
        }

        // 2. 坐标精度优化（保留6位小数，约1米精度）
        BigDecimal enhancedLon = longitude.setScale(6, RoundingMode.HALF_UP);
        BigDecimal enhancedLat = latitude.setScale(6, RoundingMode.HALF_UP);

        return new LocationInfo(enhancedLon, enhancedLat, address);
    }

    /**
     * 查找已知地标
     */
    private LocationInfo findKnownLocation(String address) {
        // 精确匹配
        LocationInfo exact = LANDMARK_COORDINATES.get(address);
        if (exact != null) {
            return exact;
        }

        // 模糊匹配
        for (Map.Entry<String, LocationInfo> entry : LANDMARK_COORDINATES.entrySet()) {
            if (address.contains(entry.getKey()) || entry.getKey().contains(address)) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * 检查坐标是否在有效范围内
     */
    private boolean isValidCoordinateRange(BigDecimal longitude, BigDecimal latitude) {
        // 中国大陆坐标范围：经度73°-135°，纬度18°-54°
        return longitude.compareTo(new BigDecimal("73")) >= 0 && 
               longitude.compareTo(new BigDecimal("135")) <= 0 &&
               latitude.compareTo(new BigDecimal("18")) >= 0 && 
               latitude.compareTo(new BigDecimal("54")) <= 0;
    }

    /**
     * 提取地址中的区域信息
     */
    private String extractRegion(String address) {
        if (address.contains("北京")) return "北京";
        if (address.contains("上海")) return "上海";
        if (address.contains("深圳")) return "深圳";
        if (address.contains("广州")) return "广州";
        if (address.contains("杭州")) return "杭州";
        if (address.contains("厦门")) return "厦门";
        return null;
    }

    /**
     * 检查坐标是否在指定区域内
     */
    private boolean isCoordinateInRegion(BigDecimal longitude, BigDecimal latitude, String region) {
        switch (region) {
            case "北京":
                return longitude.compareTo(new BigDecimal("115.4")) >= 0 && 
                       longitude.compareTo(new BigDecimal("117.5")) <= 0 &&
                       latitude.compareTo(new BigDecimal("39.4")) >= 0 && 
                       latitude.compareTo(new BigDecimal("41.1")) <= 0;
            case "上海":
                return longitude.compareTo(new BigDecimal("120.8")) >= 0 && 
                       longitude.compareTo(new BigDecimal("122.2")) <= 0 &&
                       latitude.compareTo(new BigDecimal("30.7")) >= 0 && 
                       latitude.compareTo(new BigDecimal("31.9")) <= 0;
            case "厦门":
                return longitude.compareTo(new BigDecimal("117.9")) >= 0 && 
                       longitude.compareTo(new BigDecimal("118.5")) <= 0 &&
                       latitude.compareTo(new BigDecimal("24.2")) >= 0 && 
                       latitude.compareTo(new BigDecimal("24.7")) <= 0;
            default:
                return true; // 未知区域，不进行验证
        }
    }

    /**
     * 计算两点间距离（米）
     */
    private BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        double earthRadius = 6371000; // 地球半径（米）
        
        double lat1Rad = Math.toRadians(lat1.doubleValue());
        double lat2Rad = Math.toRadians(lat2.doubleValue());
        double deltaLat = Math.toRadians(lat2.subtract(lat1).doubleValue());
        double deltaLon = Math.toRadians(lon2.subtract(lon1).doubleValue());

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return new BigDecimal(earthRadius * c).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 位置信息类
     */
    public static class LocationInfo {
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String address;

        public LocationInfo(BigDecimal longitude, BigDecimal latitude, String address) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.address = address;
        }

        // Getter和Setter方法
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    /**
     * 位置验证结果类
     */
    public static class LocationValidationResult {
        private String originalAddress;
        private BigDecimal originalLongitude;
        private BigDecimal originalLatitude;
        private boolean valid;
        private String errorMessage;
        private String accuracyLevel;
        private boolean knownLandmark;
        private BigDecimal expectedLongitude;
        private BigDecimal expectedLatitude;
        private String expectedAddress;
        private BigDecimal distanceFromExpected;
        private BigDecimal suggestedLongitude;
        private BigDecimal suggestedLatitude;

        // Getter和Setter方法（省略具体实现）
        public String getOriginalAddress() { return originalAddress; }
        public void setOriginalAddress(String originalAddress) { this.originalAddress = originalAddress; }
        
        public BigDecimal getOriginalLongitude() { return originalLongitude; }
        public void setOriginalLongitude(BigDecimal originalLongitude) { this.originalLongitude = originalLongitude; }
        
        public BigDecimal getOriginalLatitude() { return originalLatitude; }
        public void setOriginalLatitude(BigDecimal originalLatitude) { this.originalLatitude = originalLatitude; }
        
        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public String getAccuracyLevel() { return accuracyLevel; }
        public void setAccuracyLevel(String accuracyLevel) { this.accuracyLevel = accuracyLevel; }
        
        public boolean isKnownLandmark() { return knownLandmark; }
        public void setKnownLandmark(boolean knownLandmark) { this.knownLandmark = knownLandmark; }
        
        public BigDecimal getExpectedLongitude() { return expectedLongitude; }
        public void setExpectedLongitude(BigDecimal expectedLongitude) { this.expectedLongitude = expectedLongitude; }
        
        public BigDecimal getExpectedLatitude() { return expectedLatitude; }
        public void setExpectedLatitude(BigDecimal expectedLatitude) { this.expectedLatitude = expectedLatitude; }
        
        public String getExpectedAddress() { return expectedAddress; }
        public void setExpectedAddress(String expectedAddress) { this.expectedAddress = expectedAddress; }
        
        public BigDecimal getDistanceFromExpected() { return distanceFromExpected; }
        public void setDistanceFromExpected(BigDecimal distanceFromExpected) { this.distanceFromExpected = distanceFromExpected; }
        
        public BigDecimal getSuggestedLongitude() { return suggestedLongitude; }
        public void setSuggestedLongitude(BigDecimal suggestedLongitude) { this.suggestedLongitude = suggestedLongitude; }
        
        public BigDecimal getSuggestedLatitude() { return suggestedLatitude; }
        public void setSuggestedLatitude(BigDecimal suggestedLatitude) { this.suggestedLatitude = suggestedLatitude; }
    }
}
