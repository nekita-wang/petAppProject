package com.petlife.platform.advertisement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petlife.platform.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 广告信息对象 advertisement
 *
 * @author petlife
 * @date 2025-01-02
 */
public class Advertisement implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Integer id;

    /** 广告位标识（1-6） */
    @Excel(name = "广告位")
    private String adPosition;

    /** 广告自定义名称 */
    @Excel(name = "广告名")
    private String adName;

    /** 品牌方名称 */
    @Excel(name = "品牌方")
    private String brand;

    /** 状态：1-运行中，2-已结清，3-已失效 */
    @Excel(name = "状态", readConverterExp = "1=运行中,2=已结清,3=已失效")
    private Integer status;

    /** 点击量，初始为0 */
    @Excel(name = "点击量")
    private Integer clickCount;

    /** 是否结清：0-未结清，1-已结清 */
    @Excel(name = "是否结清", readConverterExp = "0=未结清,1=已结清")
    private Integer cleard;

    /** 收入（元） */
    @Excel(name = "收入(元)")
    private BigDecimal adRevenue;

    /** 打款截图文件路径 */
    private String revenueAttachment;

    /** 广告目标链接（https格式） */
    @Excel(name = "目标链接")
    private String targetUrl;

    /** 广告图片文件路径 */
    private String adImage;

    /** 广告开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "广告开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date adStartTime;

    /** 广告结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "广告结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date adEndTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人账号 */
    @Excel(name = "创建人")
    private String creator;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /** 最后更新人账号 */
    @Excel(name = "最后更新人")
    private String lastUpdater;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }

    public void setAdPosition(String adPosition) 
    {
        this.adPosition = adPosition;
    }

    public String getAdPosition() 
    {
        return adPosition;
    }

    public void setAdName(String adName) 
    {
        this.adName = adName;
    }

    public String getAdName() 
    {
        return adName;
    }

    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setClickCount(Integer clickCount) 
    {
        this.clickCount = clickCount;
    }

    public Integer getClickCount() 
    {
        return clickCount;
    }

    public void setCleard(Integer cleard) 
    {
        this.cleard = cleard;
    }

    public Integer getCleard() 
    {
        return cleard;
    }

    public void setAdRevenue(BigDecimal adRevenue) 
    {
        this.adRevenue = adRevenue;
    }

    public BigDecimal getAdRevenue() 
    {
        return adRevenue;
    }

    public void setRevenueAttachment(String revenueAttachment) 
    {
        this.revenueAttachment = revenueAttachment;
    }

    public String getRevenueAttachment() 
    {
        return revenueAttachment;
    }

    public void setTargetUrl(String targetUrl) 
    {
        this.targetUrl = targetUrl;
    }

    public String getTargetUrl() 
    {
        return targetUrl;
    }

    public void setAdImage(String adImage) 
    {
        this.adImage = adImage;
    }

    public String getAdImage() 
    {
        return adImage;
    }

    public void setAdStartTime(Date adStartTime) 
    {
        this.adStartTime = adStartTime;
    }

    public Date getAdStartTime() 
    {
        return adStartTime;
    }

    public void setAdEndTime(Date adEndTime) 
    {
        this.adEndTime = adEndTime;
    }

    public Date getAdEndTime() 
    {
        return adEndTime;
    }

    public void setCreateTime(Date createTime) 
    {
        this.createTime = createTime;
    }

    public Date getCreateTime() 
    {
        return createTime;
    }

    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }

    public void setLastUpdateTime(Date lastUpdateTime) 
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() 
    {
        return lastUpdateTime;
    }

    public void setLastUpdater(String lastUpdater) 
    {
        this.lastUpdater = lastUpdater;
    }

    public String getLastUpdater() 
    {
        return lastUpdater;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("adPosition", getAdPosition())
            .append("adName", getAdName())
            .append("brand", getBrand())
            .append("status", getStatus())
            .append("clickCount", getClickCount())
            .append("cleard", getCleard())
            .append("adRevenue", getAdRevenue())
            .append("revenueAttachment", getRevenueAttachment())
            .append("targetUrl", getTargetUrl())
            .append("adImage", getAdImage())
            .append("adStartTime", getAdStartTime())
            .append("adEndTime", getAdEndTime())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("lastUpdater", getLastUpdater())
            .toString();
    }
} 