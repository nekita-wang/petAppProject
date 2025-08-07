package com.petlife.platform.app.location.controller;

import com.petlife.platform.app.location.service.SmartLocationCacheService;
import com.petlife.platform.common.annotation.Anonymous;
import com.petlife.platform.common.core.api.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 位置反馈控制器 - 用户反馈和搜索建议
 * 
 * @author petlife
 * @date 2025-08-07
 */
@Api(tags = "位置反馈接口")
@RestController
@RequestMapping("/app/location/feedback")
@Anonymous
public class LocationFeedbackController {

    @Autowired
    private SmartLocationCacheService smartLocationCacheService;

    /**
     * 提交位置准确性反馈
     */
    @PostMapping("/accuracy")
    @ApiOperation("提交位置准确性反馈")
    public ResponseData<String> submitAccuracyFeedback(
            @ApiParam(value = "地址", required = true) @RequestParam("address") String address,
            @ApiParam(value = "是否准确", required = true) @RequestParam("isAccurate") boolean isAccurate,
            @ApiParam(value = "用户评论", required = false) @RequestParam(value = "comment", required = false) String comment) {
        
        try {
            smartLocationCacheService.recordUserFeedback(address, isAccurate, comment);
            
            String message = isAccurate ? "感谢您的反馈，这有助于我们提高定位精度" : 
                                        "感谢您的反馈，我们会改进该地址的定位准确性";
            
            return ResponseData.okWithMsg(message, null);
            
        } catch (Exception e) {
            return ResponseData.error(500, "提交反馈失败: " + e.getMessage());
        }
    }

    /**
     * 获取地址搜索建议
     */
    @GetMapping("/suggestions")
    @ApiOperation("获取地址搜索建议")
    public ResponseData<Map<String, Object>> getAddressSuggestions(
            @ApiParam(value = "搜索关键词", required = true) @RequestParam("query") String query,
            @ApiParam(value = "返回数量", required = false, example = "10") 
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        
        try {
            List<String> suggestions = smartLocationCacheService.getAddressSuggestions(query, limit);
            
            Map<String, Object> result = new HashMap<>();
            result.put("suggestions", suggestions);
            result.put("count", suggestions.size());
            result.put("query", query);
            
            return ResponseData.okWithMsg("获取建议成功", result);
            
        } catch (Exception e) {
            return ResponseData.error(500, "获取建议失败: " + e.getMessage());
        }
    }

    /**
     * 获取热门搜索地址
     */
    @GetMapping("/popular")
    @ApiOperation("获取热门搜索地址")
    public ResponseData<Map<String, Object>> getPopularAddresses(
            @ApiParam(value = "返回数量", required = false, example = "10") 
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        
        try {
            List<String> popular = smartLocationCacheService.getPopularAddresses(limit);
            
            Map<String, Object> result = new HashMap<>();
            result.put("popularAddresses", popular);
            result.put("count", popular.size());
            
            return ResponseData.okWithMsg("获取热门地址成功", result);
            
        } catch (Exception e) {
            return ResponseData.error(500, "获取热门地址失败: " + e.getMessage());
        }
    }

    /**
     * 清理低质量缓存
     */
    @PostMapping("/cleanup")
    @ApiOperation("清理低质量缓存")
    public ResponseData<String> cleanupCache() {
        try {
            smartLocationCacheService.cleanupCache();
            return ResponseData.okWithMsg("缓存清理完成", null);
        } catch (Exception e) {
            return ResponseData.error(500, "缓存清理失败: " + e.getMessage());
        }
    }

    /**
     * 获取缓存统计信息
     */
    @GetMapping("/cache/stats")
    @ApiOperation("获取缓存统计信息")
    public ResponseData<Map<String, Object>> getCacheStats() {
        try {
            // TODO: 实现缓存统计功能
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalCached", 0);
            stats.put("hitRate", 0.0);
            stats.put("avgFeedbackScore", 0.0);
            
            return ResponseData.okWithMsg("获取统计信息成功", stats);
        } catch (Exception e) {
            return ResponseData.error(500, "获取统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 批量提交反馈
     */
    @PostMapping("/batch")
    @ApiOperation("批量提交位置反馈")
    public ResponseData<Map<String, Object>> batchSubmitFeedback(
            @RequestBody List<Map<String, Object>> feedbackList) {
        
        try {
            int successCount = 0;
            int failCount = 0;
            
            for (Map<String, Object> feedback : feedbackList) {
                try {
                    String address = (String) feedback.get("address");
                    Boolean isAccurate = (Boolean) feedback.get("isAccurate");
                    String comment = (String) feedback.get("comment");
                    
                    if (address != null && isAccurate != null) {
                        smartLocationCacheService.recordUserFeedback(address, isAccurate, comment);
                        successCount++;
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    failCount++;
                }
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("total", feedbackList.size());
            result.put("success", successCount);
            result.put("failed", failCount);
            
            return ResponseData.okWithMsg("批量反馈提交完成", result);
            
        } catch (Exception e) {
            return ResponseData.error(500, "批量提交失败: " + e.getMessage());
        }
    }

    /**
     * 获取地址反馈历史
     */
    @GetMapping("/history")
    @ApiOperation("获取地址反馈历史")
    public ResponseData<Map<String, Object>> getFeedbackHistory(
            @ApiParam(value = "地址", required = true) @RequestParam("address") String address) {
        
        try {
            // TODO: 实现反馈历史查询功能
            Map<String, Object> history = new HashMap<>();
            history.put("address", address);
            history.put("feedbackCount", 0);
            history.put("avgScore", 0.0);
            history.put("recentFeedbacks", List.of());
            
            return ResponseData.okWithMsg("获取反馈历史成功", history);
            
        } catch (Exception e) {
            return ResponseData.error(500, "获取反馈历史失败: " + e.getMessage());
        }
    }
}
