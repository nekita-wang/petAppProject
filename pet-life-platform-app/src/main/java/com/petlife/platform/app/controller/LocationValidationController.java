package com.petlife.platform.app.controller;

import com.petlife.platform.app.service.LocationValidationService;
import com.petlife.platform.common.annotation.Anonymous;
import com.petlife.platform.common.core.api.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 位置验证控制器
 * 用于验证和提升位置精确度
 * 
 * @author petlife
 * @date 2025-08-07
 */
@RestController
@RequestMapping("/app/location/validation")
@Api(tags = "位置验证")
@Anonymous
public class LocationValidationController {

    @Autowired
    private LocationValidationService locationValidationService;

    /**
     * 验证坐标准确性
     */
    @GetMapping("/validate")
    @ApiOperation("验证坐标准确性")
    public ResponseData<Map<String, Object>> validateLocation(
            @ApiParam(value = "地址", required = true, example = "故宫") 
            @RequestParam("address") String address,
            @ApiParam(value = "经度", required = true, example = "116.397128") 
            @RequestParam("longitude") BigDecimal longitude,
            @ApiParam(value = "纬度", required = true, example = "39.916527") 
            @RequestParam("latitude") BigDecimal latitude) {
        
        try {
            LocationValidationService.LocationValidationResult result = 
                locationValidationService.validateLocation(address, longitude, latitude);
            
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> input = new HashMap<>();
            input.put("address", address);
            input.put("longitude", longitude);
            input.put("latitude", latitude);
            response.put("input", input);
            response.put("validation", result);
            
            if (result.isValid()) {
                return ResponseData.okWithMsg("坐标验证通过", response);
            } else {
                ResponseData<Map<String, Object>> errorResponse = ResponseData.error(400, "坐标验证失败");
                errorResponse.setData(response);
                return errorResponse;
            }
            
        } catch (Exception e) {
            return ResponseData.error(500, "验证异常: " + e.getMessage());
        }
    }

    /**
     * 获取准确坐标建议
     */
    @GetMapping("/suggest")
    @ApiOperation("获取准确坐标建议")
    public ResponseData<Map<String, Object>> suggestAccurateLocation(
            @ApiParam(value = "地址", required = true, example = "故宫") 
            @RequestParam("address") String address) {
        
        try {
            // 使用模拟坐标进行验证
            BigDecimal mockLon = new BigDecimal("101.733852");
            BigDecimal mockLat = new BigDecimal("23.817375");
            
            LocationValidationService.LocationValidationResult validation = 
                locationValidationService.validateLocation(address, mockLon, mockLat);
            
            LocationValidationService.LocationInfo enhanced = 
                locationValidationService.enhanceLocationAccuracy(address, mockLon, mockLat);
            
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> mockCoordinates = new HashMap<>();
            mockCoordinates.put("longitude", mockLon);
            mockCoordinates.put("latitude", mockLat);

            Map<String, Object> originalInput = new HashMap<>();
            originalInput.put("address", address);
            originalInput.put("mockCoordinates", mockCoordinates);
            response.put("originalInput", originalInput);
            response.put("validation", validation);
            Map<String, Object> enhancedLocation = new HashMap<>();
            enhancedLocation.put("longitude", enhanced.getLongitude());
            enhancedLocation.put("latitude", enhanced.getLatitude());
            enhancedLocation.put("address", enhanced.getAddress());
            response.put("enhancedLocation", enhancedLocation);
            
            return ResponseData.okWithMsg("获取建议成功", response);
            
        } catch (Exception e) {
            return ResponseData.error(500, "获取建议失败: " + e.getMessage());
        }
    }

    /**
     * 批量验证坐标
     */
    @PostMapping("/batch-validate")
    @ApiOperation("批量验证坐标")
    public ResponseData<Map<String, Object>> batchValidate(
            @RequestBody LocationValidationRequest[] requests) {
        
        Map<String, Object> results = new HashMap<>();
        Map<String, Object> validResults = new HashMap<>();
        Map<String, Object> invalidResults = new HashMap<>();
        
        int validCount = 0;
        int invalidCount = 0;
        
        for (LocationValidationRequest request : requests) {
            try {
                LocationValidationService.LocationValidationResult result = 
                    locationValidationService.validateLocation(
                        request.getAddress(), 
                        request.getLongitude(), 
                        request.getLatitude()
                    );
                
                if (result.isValid()) {
                    validResults.put(request.getAddress(), result);
                    validCount++;
                } else {
                    invalidResults.put(request.getAddress(), result);
                    invalidCount++;
                }
                
            } catch (Exception e) {
                Map<String, Object> errorInfo = new HashMap<>();
                errorInfo.put("error", e.getMessage() != null ? e.getMessage() : "unknown error");
                invalidResults.put(request.getAddress(), errorInfo);
                invalidCount++;
            }
        }
        
        results.put("total", requests.length);
        results.put("validCount", validCount);
        results.put("invalidCount", invalidCount);
        results.put("validResults", validResults);
        results.put("invalidResults", invalidResults);
        
        return ResponseData.okWithMsg("批量验证完成", results);
    }

    /**
     * 获取知名地标坐标
     */
    @GetMapping("/landmarks")
    @ApiOperation("获取知名地标坐标")
    public ResponseData<Map<String, Object>> getLandmarks() {
        Map<String, Object> landmarks = new HashMap<>();
        
        Map<String, Object> beijingLandmarks = new HashMap<>();
        Map<String, Object> gugong = new HashMap<>();
        gugong.put("longitude", "116.397128");
        gugong.put("latitude", "39.916527");
        beijingLandmarks.put("故宫", gugong);

        Map<String, Object> tiananmen = new HashMap<>();
        tiananmen.put("longitude", "116.397477");
        tiananmen.put("latitude", "39.909309");
        beijingLandmarks.put("天安门", tiananmen);

        Map<String, Object> pku = new HashMap<>();
        pku.put("longitude", "116.310316");
        pku.put("latitude", "39.992640");
        beijingLandmarks.put("北京大学", pku);

        Map<String, Object> tsinghua = new HashMap<>();
        tsinghua.put("longitude", "116.326734");
        tsinghua.put("latitude", "40.003078");
        beijingLandmarks.put("清华大学", tsinghua);

        landmarks.put("北京", beijingLandmarks);
        
        Map<String, Object> shanghaiLandmarks = new HashMap<>();
        Map<String, Object> waitan = new HashMap<>();
        waitan.put("longitude", "121.490317");
        waitan.put("latitude", "31.245417");
        shanghaiLandmarks.put("外滩", waitan);

        Map<String, Object> orientalPearl = new HashMap<>();
        orientalPearl.put("longitude", "121.506377");
        orientalPearl.put("latitude", "31.245105");
        shanghaiLandmarks.put("东方明珠", orientalPearl);

        landmarks.put("上海", shanghaiLandmarks);
        
        Map<String, Object> xiamenLandmarks = new HashMap<>();
        Map<String, Object> xiamenUniv = new HashMap<>();
        xiamenUniv.put("longitude", "118.096963");
        xiamenUniv.put("latitude", "24.435484");
        xiamenLandmarks.put("厦门大学", xiamenUniv);

        Map<String, Object> gulangyu = new HashMap<>();
        gulangyu.put("longitude", "118.063663");
        gulangyu.put("latitude", "24.447604");
        xiamenLandmarks.put("鼓浪屿", gulangyu);

        landmarks.put("厦门", xiamenLandmarks);
        
        Map<String, Object> shenzhenLandmarks = new HashMap<>();
        Map<String, Object> shenzhenBay = new HashMap<>();
        shenzhenBay.put("longitude", "113.969344");
        shenzhenBay.put("latitude", "22.485411");
        shenzhenLandmarks.put("深圳湾公园", shenzhenBay);

        Map<String, Object> pingAn = new HashMap<>();
        pingAn.put("longitude", "114.109497");
        pingAn.put("latitude", "22.532431");
        shenzhenLandmarks.put("平安金融中心", pingAn);

        landmarks.put("深圳", shenzhenLandmarks);
        
        return ResponseData.okWithMsg("获取地标坐标成功", landmarks);
    }

    /**
     * 计算坐标距离
     */
    @GetMapping("/distance")
    @ApiOperation("计算两点间距离")
    public ResponseData<Map<String, Object>> calculateDistance(
            @ApiParam(value = "起点经度", required = true) @RequestParam("lon1") BigDecimal lon1,
            @ApiParam(value = "起点纬度", required = true) @RequestParam("lat1") BigDecimal lat1,
            @ApiParam(value = "终点经度", required = true) @RequestParam("lon2") BigDecimal lon2,
            @ApiParam(value = "终点纬度", required = true) @RequestParam("lat2") BigDecimal lat2) {
        
        try {
            // 使用Haversine公式计算距离
            double earthRadius = 6371000; // 地球半径（米）
            
            double lat1Rad = Math.toRadians(lat1.doubleValue());
            double lat2Rad = Math.toRadians(lat2.doubleValue());
            double deltaLat = Math.toRadians(lat2.subtract(lat1).doubleValue());
            double deltaLon = Math.toRadians(lon2.subtract(lon1).doubleValue());

            double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                       Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                       Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = earthRadius * c;
            
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> point1 = new HashMap<>();
            point1.put("longitude", lon1);
            point1.put("latitude", lat1);
            result.put("point1", point1);

            Map<String, Object> point2 = new HashMap<>();
            point2.put("longitude", lon2);
            point2.put("latitude", lat2);
            result.put("point2", point2);

            Map<String, Object> distanceInfo = new HashMap<>();
            distanceInfo.put("meters", Math.round(distance));
            distanceInfo.put("kilometers", Math.round(distance / 1000.0 * 100.0) / 100.0);
            result.put("distance", distanceInfo);
            
            return ResponseData.okWithMsg("距离计算成功", result);
            
        } catch (Exception e) {
            return ResponseData.error(500, "距离计算失败: " + e.getMessage());
        }
    }

    /**
     * 位置验证请求类
     */
    public static class LocationValidationRequest {
        private String address;
        private BigDecimal longitude;
        private BigDecimal latitude;

        // Getter和Setter方法
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    }
}
