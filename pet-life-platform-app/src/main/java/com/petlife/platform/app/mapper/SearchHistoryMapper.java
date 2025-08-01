package com.petlife.platform.app.mapper;

import com.petlife.platform.app.pojo.SearchHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 搜索历史Mapper接口
 * 
 * @author petlife
 * @date 2025-07-30
 */
@Mapper
public interface SearchHistoryMapper {
    
    /**
     * 插入搜索历史记录
     * 
     * @param searchHistory 搜索历史记录
     * @return 影响行数
     */
    int insert(SearchHistory searchHistory);
    
    /**
     * 根据关键词查询搜索历史
     * 
     * @param keyword 搜索关键词
     * @return 搜索历史记录
     */
    SearchHistory selectByKeyword(@Param("keyword") String keyword);
    
    /**
     * 更新搜索历史记录
     * 
     * @param searchHistory 搜索历史记录
     * @return 影响行数
     */
    int updateByKeyword(SearchHistory searchHistory);
    
    /**
     * 获取热门搜索关键词
     * 
     * @param limit 限制数量
     * @return 热门搜索关键词列表
     */
    List<String> selectPopularKeywords(@Param("limit") int limit);
    
    /**
     * 根据关键词前缀获取搜索建议
     * 
     * @param keywordPrefix 关键词前缀
     * @param limit 限制数量
     * @return 搜索建议列表
     */
    List<String> selectSuggestionsByPrefix(@Param("keywordPrefix") String keywordPrefix, @Param("limit") int limit);
    
    /**
     * 获取成功搜索的地址建议
     * 
     * @param keywordPrefix 关键词前缀
     * @param limit 限制数量
     * @return 地址建议列表
     */
    List<String> selectSuccessfulAddressesByPrefix(@Param("keywordPrefix") String keywordPrefix, @Param("limit") int limit);
    
    /**
     * 清理过期的搜索历史记录
     * 
     * @param days 保留天数
     * @return 删除的记录数
     */
    int deleteOldRecords(@Param("days") int days);
}
