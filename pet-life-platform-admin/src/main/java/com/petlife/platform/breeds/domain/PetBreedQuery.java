package com.petlife.platform.breeds.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 宠物品种对象 pet_breed
 *
 * @author ruoyi
 * @date 2025-06-27
 */
@Data
public class PetBreedQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 宠物分类 */
    private String petClass;

    /** 宠物品种 */
    private String petBreed;


}
