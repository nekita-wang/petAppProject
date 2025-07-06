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
public class PetBreedListVo implements Serializable
{


    /** 宠物品种 */
    private String petBreed;

    /** 宠物品种（英文） */
    private String petBreedEn;

    public PetBreedListVo(String petBreed,String petBreedEn) {
        this.petBreed = petBreed;
        this.petBreedEn = petBreedEn;
    }
}
