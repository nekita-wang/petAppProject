package com.petlife.platform.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 搜索数据清理服务
 * 定期清理过期的搜索历史数据
 * 
 * @author petlife
 * @date 2025-07-30
 */
@Service
@ConditionalOnProperty(name = "search.cleanup.enable-auto-cleanup", havingValue = "true", matchIfMissing = false)
public class SearchCleanupService {
    
    @Autowired
    private SmartSearchService smartSearchService;
    
    /**
     * 定期清理过期搜索历史
     * 每天凌晨2点执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredSearchHistory() {
        try {
            int deletedCount = smartSearchService.cleanupOldSearchHistory(30);
            System.out.println("搜索历史清理完成，删除了 " + deletedCount + " 条过期记录");
        } catch (Exception e) {
            System.err.println("搜索历史清理失败: " + e.getMessage());
        }
    }
    
    /**
     * 手动清理搜索历史
     * 
     * @param days 保留天数
     * @return 清理的记录数
     */
    public int manualCleanup(int days) {
        return smartSearchService.cleanupOldSearchHistory(days);
    }
}
