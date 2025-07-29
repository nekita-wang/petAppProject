package com.petlife.platform.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户个人主页信息传输对象
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户个人主页信息")
public class UserProfileDto {
    
    @ApiModelProperty("用户ID")
    private Long userId;
    
    @ApiModelProperty("头像URL")
    private String avatarUrl;
    
    @ApiModelProperty("昵称")
    private String nickName;
    
    @ApiModelProperty("年龄")
    private Integer age;
    
    @ApiModelProperty("性别（1=男，2=女，0=未设置）")
    private Integer gender;
    
    @ApiModelProperty("性别显示文本")
    private String genderText;
    
    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    
    @ApiModelProperty("距离（米）")
    private BigDecimal distance;
    
    @ApiModelProperty("距离显示文本")
    private String distanceText;
    
    @ApiModelProperty("在线时间显示")
    private String onlineDuration;
    
    @ApiModelProperty("在线状态颜色")
    private String onlineColor;
    
    @ApiModelProperty("IP属地")
    private String ipLocation;
    
    @ApiModelProperty("个性签名")
    private String personalSignature;
    
    @ApiModelProperty("关注数")
    private Integer followCount;
    
    @ApiModelProperty("粉丝数")
    private Integer fansCount;
    
    @ApiModelProperty("是否为当前用户本人")
    private Boolean isOwner;
    
    @ApiModelProperty("当前用户是否已关注此用户")
    private Boolean isFollowed;
    
    @ApiModelProperty("宠物列表")
    private List<PetProfileInfo> petList;
    
    @ApiModelProperty("动态数量")
    private Integer postCount;
    
    @ApiModelProperty("最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;
    
    /**
     * 宠物档案信息内部类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("宠物档案信息")
    public static class PetProfileInfo {
        
        @ApiModelProperty("宠物ID")
        private String petId;
        
        @ApiModelProperty("宠物昵称")
        private String petNickName;
        
        @ApiModelProperty("宠物分类")
        private String petClass;
        
        @ApiModelProperty("宠物品种")
        private String petBreed;
        
        @ApiModelProperty("宠物性别")
        private Integer petGender;
        
        @ApiModelProperty("宠物性别显示文本")
        private String petGenderText;
        
        @ApiModelProperty("是否绝育")
        private Boolean sterilized;
        
        @ApiModelProperty("宠物生日")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate petBirthday;
        
        @ApiModelProperty("宠物年龄")
        private String petAge;
        
        @ApiModelProperty("领养日期")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate adoptionDate;
        
        @ApiModelProperty("宠物头像")
        private String petAvatarUrl;
        
        /**
         * 设置宠物性别显示文本
         */
        public void setPetGenderText() {
            if (petGender == null || petGender == 0) {
                this.petGenderText = "未设置";
            } else if (petGender == 1) {
                this.petGenderText = "公";
            } else if (petGender == 2) {
                this.petGenderText = "母";
            }
        }
        
        /**
         * 计算并设置宠物年龄
         */
        public void setPetAge() {
            if (petBirthday == null) {
                this.petAge = "未知";
                return;
            }
            
            LocalDate now = LocalDate.now();
            int years = now.getYear() - petBirthday.getYear();
            int months = now.getMonthValue() - petBirthday.getMonthValue();
            
            if (months < 0) {
                years--;
                months += 12;
            }
            
            if (years > 0) {
                this.petAge = years + "岁";
                if (months > 0) {
                    this.petAge += months + "个月";
                }
            } else if (months > 0) {
                this.petAge = months + "个月";
            } else {
                int days = now.getDayOfMonth() - petBirthday.getDayOfMonth();
                if (days < 0) {
                    months--;
                    if (months < 0) {
                        this.petAge = "未满1个月";
                    } else {
                        this.petAge = months + "个月";
                    }
                } else {
                    this.petAge = "未满1个月";
                }
            }
        }
    }
    
    /**
     * 设置性别显示文本
     */
    public void setGenderText() {
        if (gender == null || gender == 0) {
            this.genderText = "未设置";
        } else if (gender == 1) {
            this.genderText = "男";
        } else if (gender == 2) {
            this.genderText = "女";
        }
    }
    
    /**
     * 格式化距离显示
     */
    public void formatDistance() {
        if (distance == null) {
            this.distanceText = "未知";
            return;
        }
        
        BigDecimal distanceValue = distance;
        if (distanceValue.compareTo(new BigDecimal("1000")) < 0) {
            this.distanceText = distanceValue.intValue() + "m";
        } else {
            BigDecimal km = distanceValue.divide(new BigDecimal("1000"), 1, BigDecimal.ROUND_HALF_UP);
            this.distanceText = km + "km";
        }
    }
    
    /**
     * 设置在线状态和颜色
     */
    public void setOnlineStatus(String duration) {
        this.onlineDuration = duration;
        this.onlineColor = "在线".equals(duration) ? "green" : "black";
    }
    
    /**
     * 处理宠物列表的性别和年龄信息
     */
    public void processPetList() {
        if (petList != null && !petList.isEmpty()) {
            for (PetProfileInfo pet : petList) {
                pet.setPetGenderText();
                pet.setPetAge();
            }
        }
    }
}
