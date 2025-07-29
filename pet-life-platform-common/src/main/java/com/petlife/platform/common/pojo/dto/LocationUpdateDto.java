package com.petlife.platform.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 位置更新请求传输对象
 * 
 * @author petlife
 * @date 2025-07-28
 */
@Data
@ApiModel("位置更新请求")
public class LocationUpdateDto {
    
    @ApiModelProperty(value = "纬度", required = true, example = "39.90419989")
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90.0", message = "纬度必须在-90到90之间")
    @DecimalMax(value = "90.0", message = "纬度必须在-90到90之间")
    private BigDecimal latitude;
    
    @ApiModelProperty(value = "经度", required = true, example = "116.40739990")
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180.0", message = "经度必须在-180到180之间")
    @DecimalMax(value = "180.0", message = "经度必须在-180到180之间")
    private BigDecimal longitude;
    
    @ApiModelProperty(value = "定位精度（米）", example = "5.0")
    private BigDecimal accuracy;
    
    @ApiModelProperty(value = "定位类型（1=GPS，2=基站，3=WiFi，4=混合）", example = "4")
    private Byte locationType = 4;
    
    @ApiModelProperty(value = "地址描述", example = "北京市朝阳区")
    private String address;
    
    @ApiModelProperty(value = "设备类型", example = "iOS")
    private String deviceType;
    
    @ApiModelProperty(value = "APP版本", example = "1.0.0")
    private String appVersion;
}
