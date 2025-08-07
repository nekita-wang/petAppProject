package com.petlife.platform.app.service;

import com.petlife.platform.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 多源位置服务 - 通过多个地图API交叉验证提高精度
 * 
 * @author petlife
 * @date 2025-08-07
 */
@Service
public class MultiSourceLocationService {

    private static final Logger log = LoggerFactory.getLogger(MultiSourceLocationService.class);

    @Autowired
    private AmapService amapService;

    @Autowired
    private TiandituService tiandituService;

    // 可以扩展更多地图服务
    // @Autowired
    // private BaiduMapService baiduMapService;

    /**
     * 多源地理编码 - 通过多个API获取坐标并交叉验证
     * 
     * @param address 地址
     * @return 验证后的最佳坐标
     */
    public LocationResult getLocationWithValidation(String address) {
        List<LocationSource> sources = new ArrayList<>();
        
        try {
            // 1. 调用高德地图API
            Map<String, Object> amapResult = amapService.geocoding(address);
            if (amapResult != null) {
                sources.add(parseLocationSource("amap", amapResult));
            }
        } catch (Exception e) {
            log.warn("高德地图API调用失败: {}", e.getMessage());
        }

        try {
            // 2. 调用天地图API
            Map<String, Object> tiandituResult = tiandituService.geocoding(address);
            if (tiandituResult != null) {
                sources.add(parseLocationSource("tianditu", tiandituResult));
            }
        } catch (Exception e) {
            log.warn("天地图API调用失败: {}", e.getMessage());
        }

        // 3. 可以扩展更多地图服务
        // try {
        //     Map<String, Object> baiduResult = baiduMapService.geocoding(address);
        //     if (baiduResult != null) {
        //         sources.add(parseLocationSource("baidu", baiduResult));
        //     }
        // } catch (Exception e) {
        //     log.warn("百度地图API调用失败: {}", e.getMessage());
        // }

        return analyzeAndSelectBestLocation(address, sources);
    }

    /**
     * 解析地图API返回结果
     */
    private LocationSource parseLocationSource(String provider, Map<String, Object> result) {
        LocationSource source = new LocationSource();
        source.setProvider(provider);
        
        try {
            String lonStr = (String) result.get("longitude");
            String latStr = (String) result.get("latitude");
            
            if (lonStr != null && latStr != null) {
                source.setLongitude(new BigDecimal(lonStr));
                source.setLatitude(new BigDecimal(latStr));
                source.setAddress((String) result.get("address"));
                source.setValid(true);
            }
        } catch (Exception e) {
            log.warn("解析{}地图结果失败: {}", provider, e.getMessage());
            source.setValid(false);
        }
        
        return source;
    }

    /**
     * 分析多个数据源并选择最佳位置
     */
    private LocationResult analyzeAndSelectBestLocation(String originalAddress, List<LocationSource> sources) {
        LocationResult result = new LocationResult();
        result.setOriginalAddress(originalAddress);
        result.setSources(sources);

        // 过滤有效的数据源
        List<LocationSource> validSources = sources.stream()
            .filter(LocationSource::isValid)
            .collect(Collectors.toList());

        if (validSources.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMessage("所有地图API都无法解析该地址");
            return result;
        }

        if (validSources.size() == 1) {
            // 只有一个有效数据源
            LocationSource single = validSources.get(0);
            result.setSuccess(true);
            result.setLongitude(single.getLongitude());
            result.setLatitude(single.getLatitude());
            result.setConfidenceLevel("中等");
            result.setSelectedProvider(single.getProvider());
            result.setValidationMethod("单一数据源");
            return result;
        }

        // 多个数据源交叉验证
        return performCrossValidation(result, validSources);
    }

    /**
     * 执行交叉验证
     */
    private LocationResult performCrossValidation(LocationResult result, List<LocationSource> validSources) {
        // 1. 计算所有坐标的平均值
        BigDecimal avgLon = validSources.stream()
            .map(LocationSource::getLongitude)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(new BigDecimal(validSources.size()), 6, RoundingMode.HALF_UP);

        BigDecimal avgLat = validSources.stream()
            .map(LocationSource::getLatitude)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(new BigDecimal(validSources.size()), 6, RoundingMode.HALF_UP);

        // 2. 计算每个数据源与平均值的距离
        List<LocationSource> sourcesWithDistance = validSources.stream()
            .map(source -> {
                BigDecimal distance = calculateDistance(
                    source.getLatitude(), source.getLongitude(),
                    avgLat, avgLon
                );
                source.setDistanceFromAverage(distance);
                return source;
            })
            .collect(Collectors.toList());

        // 3. 分析一致性
        BigDecimal maxDistance = sourcesWithDistance.stream()
            .map(LocationSource::getDistanceFromAverage)
            .max(BigDecimal::compareTo)
            .orElse(BigDecimal.ZERO);

        String confidenceLevel;
        String validationMethod;
        BigDecimal finalLon, finalLat;

        if (maxDistance.compareTo(new BigDecimal("100")) <= 0) {
            // 所有数据源都很接近（100米内），高置信度
            confidenceLevel = "高";
            validationMethod = "多源一致性验证";
            finalLon = avgLon;
            finalLat = avgLat;
        } else if (maxDistance.compareTo(new BigDecimal("1000")) <= 0) {
            // 数据源相对接近（1公里内），中等置信度
            confidenceLevel = "中等";
            validationMethod = "多源部分一致";
            // 选择距离平均值最近的数据源
            LocationSource closest = sourcesWithDistance.stream()
                .min(Comparator.comparing(LocationSource::getDistanceFromAverage))
                .orElse(sourcesWithDistance.get(0));
            finalLon = closest.getLongitude();
            finalLat = closest.getLatitude();
            result.setSelectedProvider(closest.getProvider());
        } else {
            // 数据源差异较大，低置信度
            confidenceLevel = "低";
            validationMethod = "多源存在分歧";
            // 优先选择高德地图的结果（通常更准确）
            LocationSource preferred = sourcesWithDistance.stream()
                .filter(s -> "amap".equals(s.getProvider()))
                .findFirst()
                .orElse(sourcesWithDistance.get(0));
            finalLon = preferred.getLongitude();
            finalLat = preferred.getLatitude();
            result.setSelectedProvider(preferred.getProvider());
        }

        result.setSuccess(true);
        result.setLongitude(finalLon);
        result.setLatitude(finalLat);
        result.setConfidenceLevel(confidenceLevel);
        result.setValidationMethod(validationMethod);
        result.setMaxDistanceBetweenSources(maxDistance);

        return result;
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
     * 位置数据源
     */
    public static class LocationSource {
        private String provider;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String address;
        private boolean valid;
        private BigDecimal distanceFromAverage;

        // Getter和Setter方法
        public String getProvider() { return provider; }
        public void setProvider(String provider) { this.provider = provider; }
        
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }
        
        public BigDecimal getDistanceFromAverage() { return distanceFromAverage; }
        public void setDistanceFromAverage(BigDecimal distanceFromAverage) { this.distanceFromAverage = distanceFromAverage; }
    }

    /**
     * 位置结果
     */
    public static class LocationResult {
        private String originalAddress;
        private boolean success;
        private String errorMessage;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String confidenceLevel;
        private String validationMethod;
        private String selectedProvider;
        private BigDecimal maxDistanceBetweenSources;
        private List<LocationSource> sources;

        // Getter和Setter方法
        public String getOriginalAddress() { return originalAddress; }
        public void setOriginalAddress(String originalAddress) { this.originalAddress = originalAddress; }
        
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        
        public String getConfidenceLevel() { return confidenceLevel; }
        public void setConfidenceLevel(String confidenceLevel) { this.confidenceLevel = confidenceLevel; }
        
        public String getValidationMethod() { return validationMethod; }
        public void setValidationMethod(String validationMethod) { this.validationMethod = validationMethod; }
        
        public String getSelectedProvider() { return selectedProvider; }
        public void setSelectedProvider(String selectedProvider) { this.selectedProvider = selectedProvider; }
        
        public BigDecimal getMaxDistanceBetweenSources() { return maxDistanceBetweenSources; }
        public void setMaxDistanceBetweenSources(BigDecimal maxDistanceBetweenSources) { this.maxDistanceBetweenSources = maxDistanceBetweenSources; }
        
        public List<LocationSource> getSources() { return sources; }
        public void setSources(List<LocationSource> sources) { this.sources = sources; }
    }
}
