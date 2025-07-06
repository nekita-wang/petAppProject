package com.petlife.platform.common.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 宠物分类对象 pet_classification
 *
 * @author ruoyi
 * @date 2025-06-27
 */
@Data
public class PetClassVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 序号 */

    private String petClassId;


    /** 宠物分类 */

    private String petClass;


}
