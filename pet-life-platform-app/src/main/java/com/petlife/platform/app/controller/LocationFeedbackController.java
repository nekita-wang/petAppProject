package com.petlife.platform.app.controller;

import com.petlife.platform.app.service.SmartLocationCacheService;
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
 * 位置反馈控制器 - 用户反馈和智能学习
 * 
 * @author petlife
 * @date 2025-08-07
 */
@RestController
@RequestMapping("/app/location/feedback")
@Api(tags = "位置反馈")
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
            @ApiParam(value = "部分地址", required = true, example = "故") 
            @RequestParam("query") String query,
            @ApiParam(value = "返回数量", required = false, example = "5") 
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        
        try {
            List<String> suggestions = smartLocationCacheService.getAddressSuggestions(query, limit);
            List<String> popular = smartLocationCacheService.getPopularAddresses(10);
            
            Map<String, Object> result = new HashMap<>();
            result.put("query", query);
            result.put("suggestions", suggestions);
            result.put("popularAddresses", popular);
            
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
     * 清理缓存（管理员功能）
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
     * 批量反馈接口
     */
    @PostMapping("/batch-feedback")
    @ApiOperation("批量提交反馈")
    public ResponseData<Map<String, Object>> batchFeedback(@RequestBody FeedbackBatch feedbackBatch) {
        try {
            int successCount = 0;
            int failedCount = 0;
            
            for (FeedbackItem item : feedbackBatch.getFeedbacks()) {
                try {
                    smartLocationCacheService.recordUserFeedback(
                        item.getAddress(), 
                        item.isAccurate(), 
                        item.getComment()
                    );
                    successCount++;
                } catch (Exception e) {
                    failedCount++;
                }
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("total", feedbackBatch.getFeedbacks().size());
            result.put("success", successCount);
            result.put("failed", failedCount);
            
            return ResponseData.okWithMsg("批量反馈处理完成", result);
            
        } catch (Exception e) {
            return ResponseData.error(500, "批量反馈失败: " + e.getMessage());
        }
    }

    /**
     * 反馈批次类
     */
    public static class FeedbackBatch {
        private List<FeedbackItem> feedbacks;

        public List<FeedbackItem> getFeedbacks() { return feedbacks; }
        public void setFeedbacks(List<FeedbackItem> feedbacks) { this.feedbacks = feedbacks; }
    }

    /**
     * 反馈项类
     */
    public static class FeedbackItem {
        private String address;
        private boolean accurate;
        private String comment;

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public boolean isAccurate() { return accurate; }
        public void setAccurate(boolean accurate) { this.accurate = accurate; }
        
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
}
