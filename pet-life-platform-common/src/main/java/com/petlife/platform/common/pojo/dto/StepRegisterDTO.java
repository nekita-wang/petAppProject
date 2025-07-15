package com.petlife.platform.common.pojo.dto;

import com.petlife.platform.common.validation.PasswordMatches;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * 分步注册
 */
@Data
@ApiModel(description = "分步注册请求")
@PasswordMatches
public class StepRegisterDTO {

    @ApiModelProperty(value = "手机号", required = true, example = "13812345678")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "昵称", required = true, example = "用户昵称")
    @NotBlank(message = "昵称不能为空")
    @Size(min = 1, max = 10, message = "昵称长度必须在1-10个字符之间")
    private String nickName;

    @ApiModelProperty(value = "密码", required = true, example = "encryptedPassword")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "确认密码", required = true, example = "encryptedPassword")
    @NotBlank(message = "确认密码不能为空")
    private String passwordConfirm;

    @ApiModelProperty(value = "生日", required = true, example = "2000-01-01")
    @NotBlank(message = "生日不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "生日格式不正确，应为yyyy-MM-dd")
    private String birthday;

    @ApiModelProperty(value = "性别", required = true, example = "1", notes = "0=未设置，1=男，2=女")
    @NotNull(message = "性别不能为空")
    @Min(value = 0, message = "性别字段非法")
    @Max(value = 2, message = "性别字段非法")
    private Byte gender;

    @ApiModelProperty(value = "头像URL", required = true, example = "/profile/avatar/default.jpg")
    @NotBlank(message = "头像不能为空")
    private String avatarUrl;

    @ApiModelProperty(value = "宠物信息列表", required = false, notes = "可选，如果不填写则跳过宠物信息")
    @Valid
    private List<PetInfoDTO> pets;
} 