package com.petlife.platform.petTypes.domain;

import com.petlife.platform.common.annotation.Excel;
import com.petlife.platform.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 宠物分类对象 pet_classification
 *
 * @author ruoyi
 * @date 2025-06-27
 */
public class PetClassVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private String petClassId;


    /** 宠物分类 */
    @Excel(name = "宠物分类")
    private String petClass;


    public String getPetClassId() {
        return petClassId;
    }

    public void setPetClassId(String petClassId) {
        this.petClassId = petClassId;
    }

    public void setPetClass(String petClass)
    {
        this.petClass = petClass;
    }

    public String getPetClass()
    {
        return petClass;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)

            .append("petClass", getPetClass())


            .toString();
    }
}
