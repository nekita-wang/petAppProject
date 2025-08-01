package com.petlife.platform.app.pojo;

import java.time.LocalDateTime;

/**
 * 搜索历史记录实体
 * 用于记录用户的地址搜索历史，优化搜索建议
 * 
 * @author petlife
 * @date 2025-07-30
 */
public class SearchHistory {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 搜索关键词
     */
    private String keyword;
    
    /**
     * 搜索结果地址
     */
    private String resultAddress;
    
    /**
     * 搜索是否成功
     */
    private Boolean successful;
    
    /**
     * 搜索次数
     */
    private Integer searchCount;
    
    /**
     * 最后搜索时间
     */
    private LocalDateTime lastSearchTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    // 构造函数
    public SearchHistory() {
    }
    
    public SearchHistory(String keyword, String resultAddress, Boolean successful) {
        this.keyword = keyword;
        this.resultAddress = resultAddress;
        this.successful = successful;
        this.searchCount = 1;
        this.lastSearchTime = LocalDateTime.now();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String getResultAddress() {
        return resultAddress;
    }
    
    public void setResultAddress(String resultAddress) {
        this.resultAddress = resultAddress;
    }
    
    public Boolean getSuccessful() {
        return successful;
    }
    
    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }
    
    public Integer getSearchCount() {
        return searchCount;
    }
    
    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
    }
    
    public LocalDateTime getLastSearchTime() {
        return lastSearchTime;
    }
    
    public void setLastSearchTime(LocalDateTime lastSearchTime) {
        this.lastSearchTime = lastSearchTime;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 增加搜索次数
     */
    public void incrementSearchCount() {
        this.searchCount = (this.searchCount == null ? 0 : this.searchCount) + 1;
        this.lastSearchTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", resultAddress='" + resultAddress + '\'' +
                ", successful=" + successful +
                ", searchCount=" + searchCount +
                ", lastSearchTime=" + lastSearchTime +
                '}';
    }
}
