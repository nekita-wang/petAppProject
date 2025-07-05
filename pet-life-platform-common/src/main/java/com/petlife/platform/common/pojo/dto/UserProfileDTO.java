package com.petlife.platform.common.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class UserProfileDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotBlank(message = "昵称不能为空")
    @Length(max = 10, message = "昵称长度不能超过10字符")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "请上传头像")
    private String avatar;

    @NotNull(message = "性别不能为空")
    @Min(value = 0, message = "性别非法(0、1、2)")
    @Max(value = 2, message = "性别非法(0、1、2)")
    private Byte gender;

    @NotBlank(message = "生日不能为空")
    private String birthday;
}
