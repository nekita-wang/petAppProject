package com.petlife.platform.app.controller;

import com.petlife.platform.app.service.MapService;
import com.petlife.platform.app.service.TiandituService;
import com.petlife.platform.app.util.MapApiTestUtil;
import com.petlife.platform.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 地图API测试控制器（支持高德地图和天地图）
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Api(tags = "地图API测试")
@RestController
@RequestMapping("/app/map/test")
public class MapTestController {

    @Autowired
    private MapService mapService;

    @Autowired
    private TiandituService tiandituService;

    /**
     * 测试地理编码（地址转坐标）- 使用配置的地图服务
     */
    @ApiOperation("测试地理编码（统一接口）")
    @GetMapping("/geocoding")
    public AjaxResult testGeocoding(
            @ApiParam(value = "地址", required = true, example = "北京市朝阳区") @RequestParam("address") String address) {

        Map<String, Object> result = mapService.geocoding(address);

        if (result != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("provider", mapService.getCurrentProvider());
            response.put("data", result);
            return AjaxResult.success("地理编码成功", response);
        } else {
            return AjaxResult.error("地理编码失败");
        }
    }

    /**
     * 测试逆地理编码（坐标转地址）- 使用配置的地图服务
     */
    @ApiOperation("测试逆地理编码（统一接口）")
    @GetMapping("/reverse-geocoding")
    public AjaxResult testReverseGeocoding(
            @ApiParam(value = "经度", required = true, example = "116.40739990") @RequestParam("longitude") BigDecimal longitude,
            @ApiParam(value = "纬度", required = true, example = "39.90419989") @RequestParam("latitude") BigDecimal latitude) {

        Map<String, Object> result = mapService.reverseGeocoding(longitude, latitude);

        if (result != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("provider", mapService.getCurrentProvider());
            response.put("data", result);
            return AjaxResult.success("逆地理编码成功", response);
        } else {
            return AjaxResult.error("逆地理编码失败");
        }
    }

    /**
     * 专门测试天地图地理编码
     */
    @ApiOperation("测试天地图地理编码")
    @GetMapping("/tianditu/geocoding")
    public AjaxResult testTiandituGeocoding(
            @ApiParam(value = "地址", required = true, example = "北京市朝阳区") @RequestParam("address") String address) {

        Map<String, Object> result = tiandituService.geocoding(address);

        if (result != null) {
            return AjaxResult.success("天地图地理编码成功", result);
        } else {
            return AjaxResult.error("天地图地理编码失败");
        }
    }

    /**
     * 专门测试天地图逆地理编码
     */
    @ApiOperation("测试天地图逆地理编码")
    @GetMapping("/tianditu/reverse-geocoding")
    public AjaxResult testTiandituReverseGeocoding(
            @ApiParam(value = "经度", required = true, example = "116.40739990") @RequestParam("longitude") BigDecimal longitude,
            @ApiParam(value = "纬度", required = true, example = "39.90419989") @RequestParam("latitude") BigDecimal latitude) {

        Map<String, Object> result = tiandituService.reverseGeocoding(longitude, latitude);

        if (result != null) {
            return AjaxResult.success("天地图逆地理编码成功", result);
        } else {
            return AjaxResult.error("天地图逆地理编码失败");
        }
    }

    /**
     * 获取当前地图服务状态
     */
    @ApiOperation("获取地图服务状态")
    @GetMapping("/status")
    public AjaxResult getMapServiceStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("currentProvider", mapService.getCurrentProvider());
        status.put("isAvailable", mapService.isServiceAvailable());

        return AjaxResult.success("获取地图服务状态成功", status);
    }

    /**
     * 测试多个地址的批量地理编码
     */
    @ApiOperation("批量测试地理编码")
    @PostMapping("/batch-geocoding")
    public AjaxResult testBatchGeocoding(
            @ApiParam(value = "地址列表", required = true) @RequestBody String[] addresses) {

        Map<String, Object> results = new HashMap<>();

        for (String address : addresses) {
            Map<String, Object> result = mapService.geocoding(address);
            results.put(address, result);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("provider", mapService.getCurrentProvider());
        response.put("results", results);

        return AjaxResult.success("批量地理编码完成", response);
    }

    /**
     * 测试地图API连通性（无需真实API Key）
     */
    @ApiOperation("测试地图API连通性")
    @GetMapping("/connectivity")
    public AjaxResult testConnectivity() {
        Map<String, String> results = new HashMap<>();

        // 测试天地图连通性
        results.put("tianditu", MapApiTestUtil.testTiandituConnectivity());

        // 测试高德地图连通性
        results.put("amap", MapApiTestUtil.testAmapConnectivity());

        return AjaxResult.success("连通性测试完成", results);
    }

    /**
     * 获取天地图API申请指南
     */
    @ApiOperation("获取天地图API申请指南")
    @GetMapping("/tianditu/guide")
    public AjaxResult getTiandituGuide() {
        String guide = MapApiTestUtil.getTiandituApiGuide();
        return AjaxResult.success("获取申请指南成功", guide);
    }
}
