package com.petlife.platform.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * 位置确认请求DTO
 * 用于"附近"功能的地图定位确认
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("位置确认请求")
public class LocationConfirmDto {
    
    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    @ApiModelProperty(value = "经度", required = true, example = "118.09233")
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180.0", message = "经度值必须在-180到180之间")
    @DecimalMax(value = "180.0", message = "经度值必须在-180到180之间")
    private BigDecimal longitude;
    
    @ApiModelProperty(value = "纬度", required = true, example = "24.43985")
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90.0", message = "纬度值必须在-90到90之间")
    @DecimalMax(value = "90.0", message = "纬度值必须在-90到90之间")
    private BigDecimal latitude;
    
    @ApiModelProperty(value = "地址描述", required = false, example = "厦门大学")
    private String address;
    
    @ApiModelProperty(value = "定位来源", required = false, example = "manual")
    private String source = "manual";
    
    @ApiModelProperty(value = "设备类型", required = false, example = "iOS")
    private String deviceType;
    
    @ApiModelProperty(value = "应用版本", required = false, example = "1.0.0")
    private String appVersion;
}
