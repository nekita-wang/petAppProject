package com.petlife.platform.common.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class PetInfoDTO {

    /** 用户ID（注册时自动设置） */
    private Long userId;

    /** 宠物头像 */
    @NotBlank(message = "请上传头像")
    private String petAvatarURL;

    /** 宠物品种 */
    @NotBlank(message = "请输入宠物品种")
    private String petBreed;

    /** 宠物昵称（唯一） */
    @NotBlank(message = "昵称不能为空")
    @Length(max = 10, message = "昵称长度不能超过10字符")
    private String petNickName;

    /** 宠物分类 */
    @NotBlank(message = "宠物分类不能为空")
    private String petClass;

    /** 宠物性别(0=未设置，1=公，2=母) */
    @NotNull(message = "性别不能为空")
    @Min(value = 0, message = "性别非法(0、1、2)")
    @Max(value = 2, message = "性别非法(0、1、2)")
    private Long petGender;

    /** 是否绝育 (0=否，1=是) */
    @NotNull(message = "绝育不能为空")
    @Min(value = 0, message = "是否绝育 (0=否，1=是)")
    @Max(value = 1, message = "是否绝育 (0=否，1=是)")
    private Long sterilized;

    /** 宠物生日 */
    @NotNull(message = "宠物生日不能为空")  // 确保字段不能为空
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // 设置日期格式为 yyyy-MM-dd
    @PastOrPresent(message = "宠物生日不能晚于当前时间")  // 校验日期不能晚于当前时间
    private LocalDate petBirthday;

    /** 领养/购买日期 */
    @NotNull(message = "到家日期不能为空")  // 确保字段不能为空
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // 设置日期格式为 yyyy-MM-dd
    @PastOrPresent(message = "到家日期不能晚于当前时间")  // 校验日期不能晚于当前时间
    private LocalDate adoptionDate;

}
