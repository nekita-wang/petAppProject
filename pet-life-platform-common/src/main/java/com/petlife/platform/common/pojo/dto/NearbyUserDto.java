package com.petlife.platform.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 附近用户信息传输对象
 * 用于"附近"功能的正文展示
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("附近用户信息")
public class NearbyUserDto {
    
    @ApiModelProperty("用户ID")
    private Long userId;
    
    @ApiModelProperty("头像URL")
    private String avatarUrl;
    
    @ApiModelProperty("昵称")
    private String nickName;
    
    @ApiModelProperty("距离（米）")
    private BigDecimal distance;
    
    @ApiModelProperty("距离显示文本（如：100m、1.2km）")
    private String distanceText;
    
    @ApiModelProperty("在线时间显示")
    private String onlineDuration;
    
    @ApiModelProperty("在线状态颜色（green=在线，black=离线）")
    private String onlineColor;
    
    @ApiModelProperty("个性签名")
    private String personalSignature;
    
    @ApiModelProperty("宠物信息列表")
    private List<PetBriefInfo> petList;
    
    @ApiModelProperty("显示的宠物数量（最多2个）")
    private Integer displayPetCount;
    
    @ApiModelProperty("剩余宠物数量（超过2个时显示）")
    private Integer remainingPetCount;
    
    @ApiModelProperty("最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;
    
    /**
     * 宠物简要信息内部类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("宠物简要信息")
    public static class PetBriefInfo {
        
        @ApiModelProperty("宠物品种")
        private String petBreed;
        
        @ApiModelProperty("宠物性别（1=公，2=母）")
        private Integer petGender;
        
        @ApiModelProperty("宠物性别显示文本")
        private String petGenderText;
        
        @ApiModelProperty("显示文本（品种+性别）")
        private String displayText;
        
        /**
         * 构造函数 - 自动生成显示文本
         */
        public PetBriefInfo(String petBreed, Integer petGender) {
            this.petBreed = petBreed;
            this.petGender = petGender;
            this.petGenderText = formatGender(petGender);
            this.displayText = petBreed + this.petGenderText;
        }
        
        /**
         * 格式化性别显示
         */
        private String formatGender(Integer gender) {
            if (gender == null || gender == 0) {
                return "";
            }
            return gender == 1 ? "♂" : "♀";
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
            // 小于1000米，显示米
            this.distanceText = distanceValue.intValue() + "m";
        } else {
            // 大于等于1000米，显示公里
            BigDecimal km = distanceValue.divide(new BigDecimal("1000"), 1, BigDecimal.ROUND_HALF_UP);
            this.distanceText = km + "km";
        }
    }
    
    /**
     * 设置宠物信息并处理显示逻辑
     */
    public void setPetListWithDisplay(List<PetBriefInfo> allPets) {
        if (allPets == null || allPets.isEmpty()) {
            this.petList = null;
            this.displayPetCount = 0;
            this.remainingPetCount = 0;
            return;
        }
        
        int totalCount = allPets.size();
        if (totalCount <= 2) {
            // 宠物数量不超过2个，全部显示
            this.petList = allPets;
            this.displayPetCount = totalCount;
            this.remainingPetCount = 0;
        } else {
            // 宠物数量超过2个，只显示前2个
            this.petList = allPets.subList(0, 2);
            this.displayPetCount = 2;
            this.remainingPetCount = totalCount - 2;
        }
    }
    
    /**
     * 设置在线时间和颜色
     */
    public void setOnlineStatus(String duration) {
        this.onlineDuration = duration;
        this.onlineColor = "在线".equals(duration) ? "green" : "black";
    }
}
