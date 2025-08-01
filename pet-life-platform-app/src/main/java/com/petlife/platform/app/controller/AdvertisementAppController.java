package com.petlife.platform.app.controller;

import com.petlife.platform.common.pojo.dto.AdvertisementAppDto;
import com.petlife.platform.app.service.IAdvertisementAppService;
import com.petlife.platform.common.core.api.ResponseData;
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
    public ResponseData<AdvertisementAppDto> getAdvertisement(
            @ApiParam(value = "广告位标识(1-6)", required = true, example = "1") @PathVariable("adPosition") String adPosition) {

        log.info("APP端请求广告位 {} 的广告", adPosition);

        // 参数校验
        if (adPosition == null || adPosition.trim().isEmpty()) {
            return ResponseData.error(400, "广告位参数不能为空");
        }

        // 校验广告位范围（1-6）
        try {
            int position = Integer.parseInt(adPosition);
            if (position < 1 || position > 6) {
                return ResponseData.error(400, "广告位参数无效，应为1-6之间的数字");
            }
        } catch (NumberFormatException e) {
            return ResponseData.error(400, "广告位参数格式错误");
        }

        try {
            AdvertisementAppDto advertisement = advertisementAppService.getRunningAdvertisement(adPosition);

            if (advertisement != null) {
                log.info("APP端成功获取广告位 {} 的广告: {}", adPosition, advertisement.getAdName());
                return ResponseData.okWithMsg("获取广告成功", advertisement);
            } else {
                log.info("APP端广告位 {} 暂无运行中的广告", adPosition);
                return ResponseData.okWithMsg("暂无广告", null);
            }

        } catch (Exception e) {
            log.error("APP端获取广告位 {} 广告失败: {}", adPosition, e.getMessage(), e);
            return ResponseData.error(500, "获取广告失败");
        }
    }

    /**
     * 广告点击跳转接口（统计点击量并返回跳转链接）
     */
    @ApiOperation("广告点击跳转")
    @PostMapping("/click/{adPosition}")
    public ResponseData<String> clickAdvertisement(
            @ApiParam(value = "广告位标识(1-6)", required = true, example = "1") @PathVariable("adPosition") String adPosition) {

        log.info("APP端广告位 {} 被点击", adPosition);

        // 参数校验
        if (adPosition == null || adPosition.trim().isEmpty()) {
            return ResponseData.error(400, "广告位参数不能为空");
        }

        // 校验广告位范围（1-6）
        try {
            int position = Integer.parseInt(adPosition);
            if (position < 1 || position > 6) {
                return ResponseData.error(400, "广告位参数无效，应为1-6之间的数字");
            }
        } catch (NumberFormatException e) {
            return ResponseData.error(400, "广告位参数格式错误");
        }

        try {
            // 先获取广告信息
            AdvertisementAppDto advertisement = advertisementAppService.getRunningAdvertisement(adPosition);

            if (advertisement == null) {
                log.warn("广告位 {} 暂无运行中的广告，无法点击", adPosition);
                return ResponseData.error(404, "暂无广告可点击");
            }

            // 记录点击量
            boolean clickRecorded = advertisementAppService.recordAdClick(adPosition);

            if (clickRecorded) {
                log.info("APP端成功记录广告位 {} 的点击，跳转到: {}", adPosition, advertisement.getTargetUrl());
                // 返回跳转链接
                return ResponseData.okWithMsg("点击成功", advertisement.getTargetUrl());
            } else {
                log.warn("APP端记录广告位 {} 点击失败，但仍返回跳转链接", adPosition);
                // 即使统计失败，也要返回跳转链接，不影响用户体验
                return ResponseData.okWithMsg("点击成功", advertisement.getTargetUrl());
            }

        } catch (Exception e) {
            log.error("APP端处理广告位 {} 点击异常: {}", adPosition, e.getMessage(), e);
            return ResponseData.error(500, "点击处理异常");
        }
    }

    /**
     * 批量获取多个广告位的广告（用于首页等需要多个广告的场景）
     */
    @ApiOperation("批量获取广告")
    @GetMapping("/batch")
    public ResponseData<java.util.Map<String, AdvertisementAppDto>> getBatchAdvertisements(
            @ApiParam(value = "广告位列表，逗号分隔", required = true, example = "1,2,3") @RequestParam("adPositions") String adPositions) {

        log.info("APP端批量请求广告位: {}", adPositions);

        if (adPositions == null || adPositions.trim().isEmpty()) {
            return ResponseData.error(400, "广告位参数不能为空");
        }

        try {
            String[] positionArray = adPositions.split(",");
            java.util.Map<String, AdvertisementAppDto> result = new java.util.HashMap<>();

            for (String position : positionArray) {
                position = position.trim();
                if (!position.isEmpty()) {
                    AdvertisementAppDto ad = advertisementAppService.getRunningAdvertisement(position);
                    result.put(position, ad);
                }
            }

            log.info("APP端批量获取广告完成，返回 {} 个广告位数据", result.size());
            return ResponseData.okWithMsg("批量获取成功", result);

        } catch (Exception e) {
            log.error("APP端批量获取广告失败: {}", e.getMessage(), e);
            return ResponseData.error(500, "批量获取失败");
        }
    }
}
