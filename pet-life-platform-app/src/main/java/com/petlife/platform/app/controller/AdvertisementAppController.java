package com.petlife.platform.app.controller;

import com.petlife.platform.app.dto.AdvertisementAppDto;
import com.petlife.platform.app.service.IAdvertisementAppService;
import com.petlife.platform.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * APP端广告控制器
 * 专门为移动端提供简化的广告接口
 *
 * @author petlife
 * @date 2025-07-27
 */
@Api(tags = "APP端广告接口")
@RestController
@RequestMapping("/app/advertisement")
public class AdvertisementAppController {

    private static final Logger log = LoggerFactory.getLogger(AdvertisementAppController.class);

    @Autowired
    private IAdvertisementAppService advertisementAppService;

    /**
     * 获取广告位的运行中广告（APP端专用）
     * 返回简化的数据结构，只包含前端必需的字段
     */
    @ApiOperation("获取广告位广告")
    @GetMapping("/position/{adPosition}")
    public AjaxResult getAdvertisement(
            @ApiParam(value = "广告位标识(1-6)", required = true, example = "1")
            @PathVariable("adPosition") String adPosition) {
        
        log.info("APP端请求广告位 {} 的广告", adPosition);
        
        // 参数校验
        if (adPosition == null || adPosition.trim().isEmpty()) {
            return AjaxResult.error("广告位参数不能为空");
        }
        
        // 校验广告位范围（1-6）
        try {
            int position = Integer.parseInt(adPosition);
            if (position < 1 || position > 6) {
                return AjaxResult.error("广告位参数无效，应为1-6之间的数字");
            }
        } catch (NumberFormatException e) {
            return AjaxResult.error("广告位参数格式错误");
        }

        try {
            AdvertisementAppDto advertisement = advertisementAppService.getRunningAdvertisement(adPosition);
            
            if (advertisement != null) {
                log.info("APP端成功获取广告位 {} 的广告: {}", adPosition, advertisement.getAdName());
                return AjaxResult.success("获取广告成功", advertisement);
            } else {
                log.info("APP端广告位 {} 暂无运行中的广告", adPosition);
                return AjaxResult.success("暂无广告", null);
            }
            
        } catch (Exception e) {
            log.error("APP端获取广告位 {} 广告失败: {}", adPosition, e.getMessage(), e);
            return AjaxResult.error("获取广告失败");
        }
    }

    /**
     * 记录广告点击（用于统计，不返回广告数据）
     */
    @ApiOperation("记录广告点击")
    @PostMapping("/click/{adPosition}")
    public AjaxResult recordClick(
            @ApiParam(value = "广告位标识(1-6)", required = true, example = "1")
            @PathVariable("adPosition") String adPosition) {
        
        log.info("APP端记录广告位 {} 的点击", adPosition);
        
        // 参数校验
        if (adPosition == null || adPosition.trim().isEmpty()) {
            return AjaxResult.error("广告位参数不能为空");
        }

        try {
            boolean success = advertisementAppService.recordAdClick(adPosition);
            
            if (success) {
                log.info("APP端成功记录广告位 {} 的点击", adPosition);
                return AjaxResult.success("点击记录成功");
            } else {
                log.warn("APP端记录广告位 {} 点击失败", adPosition);
                return AjaxResult.error("点击记录失败");
            }
            
        } catch (Exception e) {
            log.error("APP端记录广告位 {} 点击异常: {}", adPosition, e.getMessage(), e);
            return AjaxResult.error("点击记录异常");
        }
    }

    /**
     * 批量获取多个广告位的广告（用于首页等需要多个广告的场景）
     */
    @ApiOperation("批量获取广告")
    @GetMapping("/batch")
    public AjaxResult getBatchAdvertisements(
            @ApiParam(value = "广告位列表，逗号分隔", required = true, example = "1,2,3")
            @RequestParam("positions") String positions) {
        
        log.info("APP端批量请求广告位: {}", positions);
        
        if (positions == null || positions.trim().isEmpty()) {
            return AjaxResult.error("广告位参数不能为空");
        }

        try {
            String[] positionArray = positions.split(",");
            java.util.Map<String, AdvertisementAppDto> result = new java.util.HashMap<>();
            
            for (String position : positionArray) {
                position = position.trim();
                if (!position.isEmpty()) {
                    AdvertisementAppDto ad = advertisementAppService.getRunningAdvertisement(position);
                    result.put(position, ad);
                }
            }
            
            log.info("APP端批量获取广告完成，返回 {} 个广告位数据", result.size());
            return AjaxResult.success("批量获取成功", result);
            
        } catch (Exception e) {
            log.error("APP端批量获取广告失败: {}", e.getMessage(), e);
            return AjaxResult.error("批量获取失败");
        }
    }
}
