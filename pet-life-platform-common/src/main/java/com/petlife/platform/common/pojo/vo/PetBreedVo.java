package com.petlife.platform.common.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 宠物品种对象 pet_breed
 *
 * @author ruoyi
 * @date 2025-06-27
 */
@Data
public class PetBreedVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 宠物品种 */
    private String petBreed;

    /** 宠物分类 */
    private String petClass;

    /** 中文首字母 */
    private String cnInitials;


    /** 宠物品种（英文） */
    private String petBreedEn;

}
