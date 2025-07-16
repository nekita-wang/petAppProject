package com.petlife.platform.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登录模块")
public class LoginDTO {

    @ApiModelProperty(value = "验证码（仅验证码登录时填写）")
    private String code;

    @ApiModelProperty(value = "密码（仅密码登录时填写）")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "登录类型：phone=验证码登录，password=密码登录")
    private String grantType;

    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    @ApiModelProperty(value = "位置")
    private String location;
}
