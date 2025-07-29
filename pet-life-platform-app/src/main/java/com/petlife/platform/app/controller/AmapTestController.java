package com.petlife.platform.app.controller;

import com.petlife.platform.app.service.AmapService;
import com.petlife.platform.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 高德地图API测试控制器
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Api(tags = "高德地图API测试")
@RestController
@RequestMapping("/app/amap/test")
public class AmapTestController {
    
    @Autowired
    private AmapService amapService;
    
    /**
     * 测试地理编码（地址转坐标）
     */
    @ApiOperation("测试地理编码")
    @GetMapping("/geocoding")
    public AjaxResult testGeocoding(
            @ApiParam(value = "地址", required = true, example = "北京市朝阳区") 
            @RequestParam("address") String address) {
        
        Map<String, Object> result = amapService.geocoding(address);
        
        if (result != null) {
            return AjaxResult.success("地理编码成功", result);
        } else {
            return AjaxResult.error("地理编码失败");
        }
    }
    
    /**
     * 测试逆地理编码（坐标转地址）
     */
    @ApiOperation("测试逆地理编码")
    @GetMapping("/reverse-geocoding")
    public AjaxResult testReverseGeocoding(
            @ApiParam(value = "经度", required = true, example = "116.40739990") 
            @RequestParam("longitude") BigDecimal longitude,
            @ApiParam(value = "纬度", required = true, example = "39.90419989") 
            @RequestParam("latitude") BigDecimal latitude) {
        
        Map<String, Object> result = amapService.reverseGeocoding(longitude, latitude);
        
        if (result != null) {
            return AjaxResult.success("逆地理编码成功", result);
        } else {
            return AjaxResult.error("逆地理编码失败");
        }
    }
}
