package com.petlife.platform.petTypes.domain;

import com.petlife.platform.common.annotation.Excel;
import com.petlife.platform.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    /** 宠物分类（英文） */

    private String petClassEn;

}
