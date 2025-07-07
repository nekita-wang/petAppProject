package com.petlife.platform.common.pojo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 宠物信息对象 pet_info
 *
 * @author ruoyi
 * @date 2025-07-06
 */
@Data
public class PetInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 宠物 ID */
    private String petId;

    /** 用户 ID */
    private Long userId;

    /** 宠物昵称（唯一） */
    private String petNickName;

    /** 宠物分类 */
    private String petClass;

    /** 宠物品种 */
    private String petBreed;

    /** 宠物性别(0=未设置，1=公，2=母) */
    private Long petGender;

    /** 是否绝育 (0=否，1=是) */
    private Long sterilized;

    /** 宠物生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate petBirthday;

    /** 领养/购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate adoptionDate;

    /** 宠物头像 */
    private String petAvatarURL;

    /** 状态 (0=生效，1=已死亡，2=已注销) */
    private Long status;

}
