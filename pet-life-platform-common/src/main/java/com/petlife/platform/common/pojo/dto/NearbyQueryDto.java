package com.petlife.platform.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

/**
 * 附近用户查询请求传输对象
 * 支持完整的筛选功能：宠物品种、距离、在线时间、年龄、性别
 *
 * @author petlife
 * @date 2025-07-28
 */
@Data
@ApiModel("附近用户查询请求")
public class NearbyQueryDto {

    @ApiModelProperty(value = "页码", example = "1")
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "20")
    @Min(value = 1, message = "每页大小必须大于0")
    @Max(value = 100, message = "每页大小不能超过100")
    private Integer pageSize = 20;

    // ==================== 距离筛选 ====================
    @ApiModelProperty(value = "距离筛选类型（1=0-1公里，2=1-3公里，3=3-5公里，4=5公里以上）", example = "1")
    private Integer distanceType;

    @ApiModelProperty(value = "自定义搜索半径（米）", example = "5000")
    @Min(value = 100, message = "搜索半径不能小于100米")
    @Max(value = 50000, message = "搜索半径不能超过50公里")
    private Integer radius;

    @ApiModelProperty(value = "最小距离（米）", example = "0")
    private Integer minDistance = 0;

    @ApiModelProperty(value = "最大距离（米）", example = "1000")
    private Integer maxDistance;

    // ==================== 宠物品种筛选 ====================
    @ApiModelProperty(value = "宠物品种关键词（支持模糊搜索）", example = "布偶")
    private String petBreedKeyword;

    @ApiModelProperty(value = "精确宠物品种筛选", example = "[\"布偶猫\", \"哈士奇\"]")
    private List<String> petBreeds;

    // ==================== 用户属性筛选 ====================
    @ApiModelProperty(value = "性别筛选（1=男，2=女）", example = "[1, 2]")
    private List<Integer> genders;

    @ApiModelProperty(value = "年龄范围筛选 - 最小年龄", example = "18")
    @Min(value = 0, message = "最小年龄不能小于0")
    @Max(value = 100, message = "最小年龄不能超过100")
    private Integer minAge;

    @ApiModelProperty(value = "年龄范围筛选 - 最大年龄", example = "35")
    @Min(value = 0, message = "最大年龄不能小于0")
    @Max(value = 100, message = "最大年龄不能超过100")
    private Integer maxAge;

    // ==================== 在线时间筛选 ====================
    @ApiModelProperty(value = "在线时间筛选类型（1=在线，2=1小时内，3=1天内，4=1周内，5=1月内）", example = "2")
    private Integer onlineTimeType;

    @ApiModelProperty(value = "自定义在线时间筛选（分钟）", example = "60")
    private Integer onlineTimeLimit;

    @ApiModelProperty(value = "是否只显示在线用户", example = "false")
    private Boolean onlineOnly = false;

    // ==================== 搜索和排序 ====================
    @ApiModelProperty(value = "关键词搜索（昵称或个性签名）", example = "爱狗人士")
    private String keyword;

    @ApiModelProperty(value = "排序方式（distance=距离，online_time=在线时间，register_time=注册时间）", example = "distance")
    private String sortBy = "distance";

    @ApiModelProperty(value = "排序方向（asc=升序，desc=降序）", example = "asc")
    private String sortOrder = "asc";

    // ==================== 定位相关 ====================
    @ApiModelProperty(value = "用户当前纬度", example = "39.90419989")
    private BigDecimal userLatitude;

    @ApiModelProperty(value = "用户当前经度", example = "116.40739990")
    private BigDecimal userLongitude;

    @ApiModelProperty(value = "是否使用自定义定位", example = "false")
    private Boolean useCustomLocation = false;

    @ApiModelProperty(value = "自定义定位地址", example = "北京市朝阳区")
    private String customLocationAddress;

    /**
     * 根据距离类型设置距离范围
     */
    public void setDistanceRangeByType() {
        if (distanceType == null) {
            return;
        }

        switch (distanceType) {
            case 1: // 0-1公里
                this.minDistance = 0;
                this.maxDistance = 1000;
                this.radius = 1000;
                break;
            case 2: // 1-3公里
                this.minDistance = 1000;
                this.maxDistance = 3000;
                this.radius = 3000;
                break;
            case 3: // 3-5公里
                this.minDistance = 3000;
                this.maxDistance = 5000;
                this.radius = 5000;
                break;
            case 4: // 5公里以上
                this.minDistance = 5000;
                this.maxDistance = 50000; // 最大50公里
                this.radius = 50000;
                break;
            default:
                this.radius = 5000; // 默认5公里
                break;
        }
    }

    /**
     * 根据在线时间类型设置在线时间限制
     */
    public void setOnlineTimeLimitByType() {
        if (onlineTimeType == null) {
            return;
        }

        switch (onlineTimeType) {
            case 1: // 在线
                this.onlineOnly = true;
                this.onlineTimeLimit = 1; // 1分钟内
                break;
            case 2: // 1小时内
                this.onlineTimeLimit = 60;
                break;
            case 3: // 1天内
                this.onlineTimeLimit = 24 * 60;
                break;
            case 4: // 1周内
                this.onlineTimeLimit = 7 * 24 * 60;
                break;
            case 5: // 1月内
                this.onlineTimeLimit = 30 * 24 * 60;
                break;
            default:
                break;
        }
    }
}
