package com.petlife.platform.app.service;

import com.petlife.platform.app.mapper.SearchHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 智能位置缓存服务 - 基于历史数据和用户反馈的学习系统
 * 
 * @author petlife
 * @date 2025-08-07
 */
@Service
public class SmartLocationCacheService {

    private static final Logger log = LoggerFactory.getLogger(SmartLocationCacheService.class);
    
    private static final String CACHE_PREFIX = "location:cache:";
    private static final String FEEDBACK_PREFIX = "location:feedback:";
    private static final String POPULAR_PREFIX = "location:popular:";
    
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    /**
     * 获取缓存的位置信息
     * 
     * @param address 地址
     * @return 缓存的位置信息，如果没有则返回null
     */
    public CachedLocation getCachedLocation(String address) {
        try {
            String normalizedAddress = normalizeAddress(address);
            String cacheKey = CACHE_PREFIX + normalizedAddress;
            
            CachedLocation cached = (CachedLocation) redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) {
                // 更新访问次数和时间
                cached.setAccessCount(cached.getAccessCount() + 1);
                cached.setLastAccessTime(LocalDateTime.now());
                
                // 延长缓存时间（热点数据）
                redisTemplate.expire(cacheKey, calculateCacheExpiration(cached), TimeUnit.HOURS);
                
                log.info("从缓存获取位置信息: {} -> {},{}", address, cached.getLongitude(), cached.getLatitude());
                return cached;
            }
        } catch (Exception e) {
            log.error("获取缓存位置信息失败: {}", e.getMessage());
        }
        
        return null;
    }

    /**
     * 缓存位置信息
     * 
     * @param address 地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param provider 数据提供商
     * @param confidenceLevel 置信度
     */
    public void cacheLocation(String address, BigDecimal longitude, BigDecimal latitude, 
                             String provider, String confidenceLevel) {
        try {
            String normalizedAddress = normalizeAddress(address);
            String cacheKey = CACHE_PREFIX + normalizedAddress;
            
            CachedLocation cached = new CachedLocation();
            cached.setAddress(address);
            cached.setNormalizedAddress(normalizedAddress);
            cached.setLongitude(longitude);
            cached.setLatitude(latitude);
            cached.setProvider(provider);
            cached.setConfidenceLevel(confidenceLevel);
            cached.setCreateTime(LocalDateTime.now());
            cached.setLastAccessTime(LocalDateTime.now());
            cached.setAccessCount(1);
            cached.setFeedbackScore(0.0);
            cached.setFeedbackCount(0);
            
            // 根据置信度设置不同的缓存时间
            int cacheHours = calculateInitialCacheExpiration(confidenceLevel);
            redisTemplate.opsForValue().set(cacheKey, cached, cacheHours, TimeUnit.HOURS);
            
            // 更新热门地址统计
            updatePopularityStats(normalizedAddress);
            
            log.info("缓存位置信息: {} -> {},{} ({}小时)", address, longitude, latitude, cacheHours);
            
        } catch (Exception e) {
            log.error("缓存位置信息失败: {}", e.getMessage());
        }
    }

    /**
     * 记录用户反馈
     * 
     * @param address 地址
     * @param isAccurate 是否准确
     * @param userComment 用户评论
     */
    public void recordUserFeedback(String address, boolean isAccurate, String userComment) {
        try {
            String normalizedAddress = normalizeAddress(address);
            String cacheKey = CACHE_PREFIX + normalizedAddress;
            String feedbackKey = FEEDBACK_PREFIX + normalizedAddress;
            
            // 更新缓存中的反馈信息
            CachedLocation cached = (CachedLocation) redisTemplate.opsForValue().get(cacheKey);
            if (cached != null) {
                double newScore = calculateNewFeedbackScore(cached, isAccurate);
                cached.setFeedbackScore(newScore);
                cached.setFeedbackCount(cached.getFeedbackCount() + 1);
                
                // 如果反馈分数太低，缩短缓存时间或删除缓存
                if (newScore < 0.3 && cached.getFeedbackCount() >= 3) {
                    redisTemplate.delete(cacheKey);
                    log.warn("删除低评分缓存: {} (评分: {})", address, newScore);
                } else {
                    redisTemplate.opsForValue().set(cacheKey, cached, 
                        calculateCacheExpiration(cached), TimeUnit.HOURS);
                }
            }
            
            // 记录详细反馈
            UserFeedback feedback = new UserFeedback();
            feedback.setAddress(address);
            feedback.setNormalizedAddress(normalizedAddress);
            feedback.setIsAccurate(isAccurate);
            feedback.setUserComment(userComment);
            feedback.setFeedbackTime(LocalDateTime.now());
            
            redisTemplate.opsForList().leftPush(feedbackKey, feedback);
            redisTemplate.expire(feedbackKey, 30, TimeUnit.DAYS);
            
            log.info("记录用户反馈: {} - {}", address, isAccurate ? "准确" : "不准确");
            
        } catch (Exception e) {
            log.error("记录用户反馈失败: {}", e.getMessage());
        }
    }

    /**
     * 获取热门搜索地址
     * 
     * @param limit 返回数量限制
     * @return 热门地址列表
     */
    public List<String> getPopularAddresses(int limit) {
        try {
            Set<Object> popularSet = redisTemplate.opsForZSet()
                .reverseRange(POPULAR_PREFIX + "addresses", 0, limit - 1);
            
            return popularSet != null ? 
                new ArrayList<>((Set<String>) (Set<?>) popularSet) : 
                new ArrayList<>();
                
        } catch (Exception e) {
            log.error("获取热门地址失败: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 获取地址建议（基于历史搜索和相似性）
     * 
     * @param partialAddress 部分地址
     * @param limit 返回数量限制
     * @return 地址建议列表
     */
    public List<String> getAddressSuggestions(String partialAddress, int limit) {
        try {
            String normalizedPartial = normalizeAddress(partialAddress);
            List<String> suggestions = new ArrayList<>();
            
            // 1. 从搜索历史中获取建议
            List<String> historyResults = searchHistoryMapper
                .selectSuccessfulAddressesByPrefix(normalizedPartial, limit);
            suggestions.addAll(historyResults);
            
            // 2. 从缓存中获取相似地址
            Set<Object> cacheKeysObj = redisTemplate.keys(CACHE_PREFIX + "*" + normalizedPartial + "*");
            if (cacheKeysObj != null) {
                for (Object keyObj : cacheKeysObj) {
                    if (suggestions.size() >= limit) break;

                    String key = (String) keyObj;
                    CachedLocation cached = (CachedLocation) redisTemplate.opsForValue().get(key);
                    if (cached != null && cached.getFeedbackScore() > 0.5) {
                        suggestions.add(cached.getAddress());
                    }
                }
            }
            
            // 3. 去重并限制数量
            return suggestions.stream()
                .distinct()
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
                
        } catch (Exception e) {
            log.error("获取地址建议失败: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 清理过期和低质量缓存
     */
    public void cleanupCache() {
        try {
            Set<Object> cacheKeysObj = redisTemplate.keys(CACHE_PREFIX + "*");
            if (cacheKeysObj == null) return;

            int cleanedCount = 0;
            for (Object keyObj : cacheKeysObj) {
                String key = (String) keyObj;
                CachedLocation cached = (CachedLocation) redisTemplate.opsForValue().get(key);
                if (cached != null) {
                    // 清理条件：反馈分数低且访问次数少
                    if (cached.getFeedbackScore() < 0.2 && cached.getAccessCount() < 5) {
                        redisTemplate.delete(key);
                        cleanedCount++;
                    }
                }
            }
            
            log.info("清理缓存完成，删除了{}个低质量缓存项", cleanedCount);
            
        } catch (Exception e) {
            log.error("清理缓存失败: {}", e.getMessage());
        }
    }

    /**
     * 地址标准化
     * 移除空格、标点符号，转换为小写，便于缓存匹配
     */
    private String normalizeAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "";
        }

        return address.trim()
            .toLowerCase()
            // 移除所有空白字符
            .replaceAll("\\s+", "")
            // 移除中文标点符号
            .replaceAll("[，。！？；：\"\"''（）]", "")
            // 移除英文标点符号和特殊字符 (注意转义)
            .replaceAll("[,.!?;:\"'()\\[\\]{}]", "")
            // 移除其他可能的分隔符 (注意-要放在最后或转义)
            .replaceAll("[_/\\\\|\\-]", "");
    }

    /**
     * 计算初始缓存过期时间
     */
    private int calculateInitialCacheExpiration(String confidenceLevel) {
        switch (confidenceLevel) {
            case "高": return 168; // 7天
            case "中等": return 72; // 3天
            case "低": return 24; // 1天
            default: return 48; // 2天
        }
    }

    /**
     * 计算动态缓存过期时间
     */
    private int calculateCacheExpiration(CachedLocation cached) {
        int baseHours = calculateInitialCacheExpiration(cached.getConfidenceLevel());
        
        // 根据访问频率和反馈质量调整
        double multiplier = 1.0;
        
        if (cached.getAccessCount() > 10) multiplier += 0.5;
        if (cached.getFeedbackScore() > 0.8) multiplier += 0.3;
        if (cached.getFeedbackScore() < 0.5) multiplier -= 0.3;
        
        return Math.max(12, (int) (baseHours * multiplier)); // 最少12小时
    }

    /**
     * 计算新的反馈分数
     */
    private double calculateNewFeedbackScore(CachedLocation cached, boolean isAccurate) {
        double currentScore = cached.getFeedbackScore();
        int currentCount = cached.getFeedbackCount();
        
        double newFeedback = isAccurate ? 1.0 : 0.0;
        
        // 加权平均，新反馈权重更高
        double weight = Math.min(0.3, 1.0 / (currentCount + 1));
        return currentScore * (1 - weight) + newFeedback * weight;
    }

    /**
     * 更新热门度统计
     */
    private void updatePopularityStats(String normalizedAddress) {
        try {
            redisTemplate.opsForZSet().incrementScore(
                POPULAR_PREFIX + "addresses", normalizedAddress, 1.0);
            redisTemplate.expire(POPULAR_PREFIX + "addresses", 30, TimeUnit.DAYS);
        } catch (Exception e) {
            log.error("更新热门度统计失败: {}", e.getMessage());
        }
    }

    /**
     * 缓存位置信息类
     */
    public static class CachedLocation {
        private String address;
        private String normalizedAddress;
        private BigDecimal longitude;
        private BigDecimal latitude;
        private String provider;
        private String confidenceLevel;
        private LocalDateTime createTime;
        private LocalDateTime lastAccessTime;
        private int accessCount;
        private double feedbackScore;
        private int feedbackCount;

        // Getter和Setter方法（省略具体实现）
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public String getNormalizedAddress() { return normalizedAddress; }
        public void setNormalizedAddress(String normalizedAddress) { this.normalizedAddress = normalizedAddress; }
        
        public BigDecimal getLongitude() { return longitude; }
        public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
        
        public BigDecimal getLatitude() { return latitude; }
        public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
        
        public String getProvider() { return provider; }
        public void setProvider(String provider) { this.provider = provider; }
        
        public String getConfidenceLevel() { return confidenceLevel; }
        public void setConfidenceLevel(String confidenceLevel) { this.confidenceLevel = confidenceLevel; }
        
        public LocalDateTime getCreateTime() { return createTime; }
        public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
        
        public LocalDateTime getLastAccessTime() { return lastAccessTime; }
        public void setLastAccessTime(LocalDateTime lastAccessTime) { this.lastAccessTime = lastAccessTime; }
        
        public int getAccessCount() { return accessCount; }
        public void setAccessCount(int accessCount) { this.accessCount = accessCount; }
        
        public double getFeedbackScore() { return feedbackScore; }
        public void setFeedbackScore(double feedbackScore) { this.feedbackScore = feedbackScore; }
        
        public int getFeedbackCount() { return feedbackCount; }
        public void setFeedbackCount(int feedbackCount) { this.feedbackCount = feedbackCount; }
    }

    /**
     * 用户反馈类
     */
    public static class UserFeedback {
        private String address;
        private String normalizedAddress;
        private boolean isAccurate;
        private String userComment;
        private LocalDateTime feedbackTime;

        // Getter和Setter方法（省略具体实现）
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public String getNormalizedAddress() { return normalizedAddress; }
        public void setNormalizedAddress(String normalizedAddress) { this.normalizedAddress = normalizedAddress; }
        
        public boolean isAccurate() { return isAccurate; }
        public void setIsAccurate(boolean accurate) { isAccurate = accurate; }
        
        public String getUserComment() { return userComment; }
        public void setUserComment(String userComment) { this.userComment = userComment; }
        
        public LocalDateTime getFeedbackTime() { return feedbackTime; }
        public void setFeedbackTime(LocalDateTime feedbackTime) { this.feedbackTime = feedbackTime; }
    }
}
