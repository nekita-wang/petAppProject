package com.petlife.platform.advertisement.domain;

import java.math.BigDecimal;

/**
 * 广告统计数据对象
 *
 * @author petlife
 * @date 2025-01-02
 */
public class AdvertisementStatistics
{
    /** 总广告数 */
    private Integer totalAds;

    /** 运行中广告数 */
    private Integer runningAds;

    /** 已结清广告数 */
    private Integer clearedAds;

    /** 已失效广告数 */
    private Integer invalidAds;

    /** 总点击量 */
    private Integer totalClicks;

    /** 总收入 */
    private BigDecimal totalRevenue;

    /** 平均点击量 */
    private Double avgClicks;

    /** 平均收入 */
    private BigDecimal avgRevenue;

    public Integer getTotalAds() {
        return totalAds;
    }

    public void setTotalAds(Integer totalAds) {
        this.totalAds = totalAds;
    }

    public Integer getRunningAds() {
        return runningAds;
    }

    public void setRunningAds(Integer runningAds) {
        this.runningAds = runningAds;
    }

    public Integer getClearedAds() {
        return clearedAds;
    }

    public void setClearedAds(Integer clearedAds) {
        this.clearedAds = clearedAds;
    }

    public Integer getInvalidAds() {
        return invalidAds;
    }

    public void setInvalidAds(Integer invalidAds) {
        this.invalidAds = invalidAds;
    }

    public Integer getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(Integer totalClicks) {
        this.totalClicks = totalClicks;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getAvgClicks() {
        return avgClicks;
    }

    public void setAvgClicks(Double avgClicks) {
        this.avgClicks = avgClicks;
    }

    public BigDecimal getAvgRevenue() {
        return avgRevenue;
    }

    public void setAvgRevenue(BigDecimal avgRevenue) {
        this.avgRevenue = avgRevenue;
    }

    @Override
    public String toString() {
        return "AdvertisementStatistics{" +
                "totalAds=" + totalAds +
                ", runningAds=" + runningAds +
                ", clearedAds=" + clearedAds +
                ", invalidAds=" + invalidAds +
                ", totalClicks=" + totalClicks +
                ", totalRevenue=" + totalRevenue +
                ", avgClicks=" + avgClicks +
                ", avgRevenue=" + avgRevenue +
                '}';
    }
}
