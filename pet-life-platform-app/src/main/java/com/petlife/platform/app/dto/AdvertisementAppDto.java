package com.petlife.platform.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * APP端广告信息传输对象
 * 只包含前端必需的字段，简化数据传输
 *
 * @author petlife
 * @date 2025-07-27
 */
@ApiModel("APP端广告信息")
public class AdvertisementAppDto {

    @ApiModelProperty("广告位标识")
    private String adPosition;

    @ApiModelProperty("广告名称")
    private String adName;

    @ApiModelProperty("品牌名称")
    private String brand;

    @ApiModelProperty("广告图片路径")
    private String adImage;

    @ApiModelProperty("目标链接")
    private String targetUrl;

    @ApiModelProperty("点击量")
    private Integer clickCount;

    @ApiModelProperty("广告开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adStartTime;

    @ApiModelProperty("广告结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adEndTime;

    public AdvertisementAppDto() {
    }

    public AdvertisementAppDto(String adPosition, String adName, String brand, 
                              String adImage, String targetUrl, Integer clickCount,
                              Date adStartTime, Date adEndTime) {
        this.adPosition = adPosition;
        this.adName = adName;
        this.brand = brand;
        this.adImage = adImage;
        this.targetUrl = targetUrl;
        this.clickCount = clickCount;
        this.adStartTime = adStartTime;
        this.adEndTime = adEndTime;
    }

    public String getAdPosition() {
        return adPosition;
    }

    public void setAdPosition(String adPosition) {
        this.adPosition = adPosition;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAdImage() {
        return adImage;
    }

    public void setAdImage(String adImage) {
        this.adImage = adImage;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Date getAdStartTime() {
        return adStartTime;
    }

    public void setAdStartTime(Date adStartTime) {
        this.adStartTime = adStartTime;
    }

    public Date getAdEndTime() {
        return adEndTime;
    }

    public void setAdEndTime(Date adEndTime) {
        this.adEndTime = adEndTime;
    }

    @Override
    public String toString() {
        return "AdvertisementAppDto{" +
                "adPosition='" + adPosition + '\'' +
                ", adName='" + adName + '\'' +
                ", brand='" + brand + '\'' +
                ", adImage='" + adImage + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", clickCount=" + clickCount +
                ", adStartTime=" + adStartTime +
                ", adEndTime=" + adEndTime +
                '}';
    }
}
