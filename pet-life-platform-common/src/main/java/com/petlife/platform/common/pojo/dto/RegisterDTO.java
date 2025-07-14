package com.petlife.platform.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "用户注册请求")
public class RegisterDTO {

    @ApiModelProperty(value = "手机号", required = true, example = "13812345678")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "验证码", required = true, example = "123456")
    @NotBlank(message = "验证码不能为空")
    @Size(min = 6, max = 6, message = "验证码长度必须为6位")
    private String code;

    @ApiModelProperty(value = "昵称", required = true, example = "用户昵称")
    @NotBlank(message = "昵称不能为空")
    @Size(min = 1, max = 10, message = "昵称长度必须在1-10个字符之间")
    private String nickName;

    @ApiModelProperty(value = "密码", required = true, example = "encryptedPassword")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "生日", required = true, example = "2000-01-01")
    @NotBlank(message = "生日不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "生日格式不正确，应为yyyy-MM-dd")
    private String birthday;

    @ApiModelProperty(value = "性别", required = true, example = "1", notes = "0=未设置，1=男，2=女")
    private Byte gender;

    @ApiModelProperty(value = "是否未成年", required = true, example = "0", notes = "0=成年，1=未成年")
    private Byte minor;
} 