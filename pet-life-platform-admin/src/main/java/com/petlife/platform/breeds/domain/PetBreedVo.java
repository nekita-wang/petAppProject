package com.petlife.platform.breeds.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petlife.platform.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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



    /** 宠物分类 */
    private String petClass;

    /** 中文首字母 */
    private String cnInitials;

    /** 宠物品种 */
    private String petBreed;



}
