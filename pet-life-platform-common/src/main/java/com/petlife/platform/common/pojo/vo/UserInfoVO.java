package com.petlife.platform.common.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息视图对象")
public class UserInfoVO {

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别")
    private Byte gender;

    @ApiModelProperty("头像URL")
    private String avatarUrl;

    @ApiModelProperty("出生日期")
    private String birthday;
}
