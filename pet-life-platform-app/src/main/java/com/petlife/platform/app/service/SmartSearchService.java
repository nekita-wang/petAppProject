package com.petlife.platform.app.service;

import com.petlife.platform.app.mapper.SearchHistoryMapper;
import com.petlife.platform.app.pojo.SearchHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 智能搜索服务
 * 基于搜索历史提供智能搜索建议和优化
 * 
 * @author petlife
 * @date 2025-07-30
 */
@Service
public class SmartSearchService {
    
    @Autowired
    private SearchHistoryMapper searchHistoryMapper;
    
    /**
     * 记录搜索历史
     * 
     * @param keyword 搜索关键词
     * @param resultAddress 搜索结果地址
     * @param successful 搜索是否成功
     */
    public void recordSearchHistory(String keyword, String resultAddress, boolean successful) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }
        
        try {
            String trimmedKeyword = keyword.trim();
            SearchHistory existingHistory = searchHistoryMapper.selectByKeyword(trimmedKeyword);
            
            if (existingHistory != null) {
                // 更新现有记录
                existingHistory.incrementSearchCount();
                if (successful && resultAddress != null) {
                    existingHistory.setResultAddress(resultAddress);
                    existingHistory.setSuccessful(true);
                }
                searchHistoryMapper.updateByKeyword(existingHistory);
            } else {
                // 创建新记录
                SearchHistory newHistory = new SearchHistory(trimmedKeyword, resultAddress, successful);
                searchHistoryMapper.insert(newHistory);
            }
        } catch (Exception e) {
            // 记录搜索历史失败不应该影响主要功能
            System.err.println("记录搜索历史失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取智能搜索建议
     * 结合历史搜索、热门搜索和内置建议
     * 
     * @param keyword 搜索关键词
     * @param limit 建议数量限制
     * @return 搜索建议列表
     */
    public List<String> getSmartSuggestions(String keyword, Integer limit) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getPopularSuggestions(limit);
        }
        
        String trimmedKeyword = keyword.trim();
        int maxLimit = limit != null ? limit : 5;
        List<String> suggestions = new ArrayList<>();
        
        try {
            // 1. 从搜索历史中获取建议
            List<String> historySuggestions = searchHistoryMapper.selectSuggestionsByPrefix(trimmedKeyword, maxLimit);
            suggestions.addAll(historySuggestions);
            
            // 2. 从成功搜索的地址中获取建议
            if (suggestions.size() < maxLimit) {
                List<String> addressSuggestions = searchHistoryMapper.selectSuccessfulAddressesByPrefix(
                    trimmedKeyword, maxLimit - suggestions.size());
                suggestions.addAll(addressSuggestions);
            }
            
            // 3. 添加内置建议
            if (suggestions.size() < maxLimit) {
                List<String> builtInSuggestions = getBuiltInSuggestions(trimmedKeyword, maxLimit - suggestions.size());
                suggestions.addAll(builtInSuggestions);
            }
            
        } catch (Exception e) {
            System.err.println("获取历史建议失败，使用内置建议: " + e.getMessage());
            // 如果数据库查询失败，使用内置建议
            suggestions = getBuiltInSuggestions(trimmedKeyword, maxLimit);
        }
        
        // 去重并限制数量
        return suggestions.stream()
            .distinct()
            .limit(maxLimit)
            .collect(Collectors.toList());
    }
    
    /**
     * 获取热门搜索建议
     * 
     * @param limit 建议数量限制
     * @return 热门搜索建议列表
     */
    public List<String> getPopularSuggestions(Integer limit) {
        int maxLimit = limit != null ? limit : 5;
        
        try {
            List<String> popularKeywords = searchHistoryMapper.selectPopularKeywords(maxLimit);
            if (!popularKeywords.isEmpty()) {
                return popularKeywords;
            }
        } catch (Exception e) {
            System.err.println("获取热门搜索失败: " + e.getMessage());
        }
        
        // 如果没有历史数据，返回默认热门建议
        return getDefaultPopularSuggestions(maxLimit);
    }
    
    /**
     * 获取内置搜索建议
     * 
     * @param keyword 搜索关键词
     * @param limit 建议数量限制
     * @return 内置建议列表
     */
    private List<String> getBuiltInSuggestions(String keyword, int limit) {
        List<String> suggestions = new ArrayList<>();
        
        // 基于关键词生成建议
        if (keyword.contains("外滩")) {
            suggestions.add("上海外滩观光隧道");
            suggestions.add("外滩18号");
            suggestions.add("外滩金融中心");
            suggestions.add("外滩源");
        } else if (keyword.contains("天安门")) {
            suggestions.add("天安门广场");
            suggestions.add("天安门城楼");
            suggestions.add("人民大会堂");
            suggestions.add("故宫博物院");
        } else if (keyword.contains("西湖")) {
            suggestions.add("西湖断桥");
            suggestions.add("雷峰塔");
            suggestions.add("苏堤");
            suggestions.add("白堤");
        } else if (keyword.contains("机场")) {
            suggestions.add(keyword.replace("机场", "") + "国际机场");
            suggestions.add(keyword.replace("机场", "") + "机场T1航站楼");
            suggestions.add(keyword.replace("机场", "") + "机场T2航站楼");
        } else {
            // 通用建议
            suggestions.add(keyword + "地铁站");
            suggestions.add(keyword + "商场");
            suggestions.add(keyword + "医院");
            suggestions.add(keyword + "公园");
            suggestions.add(keyword + "大学");
        }
        
        return suggestions.stream()
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    /**
     * 获取默认热门建议
     * 
     * @param limit 建议数量限制
     * @return 默认热门建议列表
     */
    private List<String> getDefaultPopularSuggestions(int limit) {
        List<String> defaultSuggestions = Arrays.asList(
            "北京天安门",
            "上海外滩",
            "杭州西湖",
            "深圳腾讯大厦",
            "广州塔",
            "成都春熙路",
            "西安钟楼",
            "南京夫子庙"
        );
        
        return defaultSuggestions.stream()
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    /**
     * 清理过期的搜索历史记录
     * 建议定期调用此方法清理数据
     * 
     * @param days 保留天数，默认30天
     * @return 清理的记录数
     */
    public int cleanupOldSearchHistory(int days) {
        try {
            return searchHistoryMapper.deleteOldRecords(days > 0 ? days : 30);
        } catch (Exception e) {
            System.err.println("清理搜索历史失败: " + e.getMessage());
            return 0;
        }
    }
}
