package com.petlife.platform.breeds.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petlife.platform.common.annotation.Excel;
import com.petlife.platform.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 宠物品种对象 pet_breed
 *
 * @author ruoyi
 * @date 2025-06-27
 */
public class PetBreed implements Serializable
{
    private static final long serialVersionUID = 1L;


    /** 中文排序 */
    @Excel(name = "中文排序")
    private String cnNo;

    /** 英文排序 */
    @Excel(name = "英文排序")
    private String enNo;

    /** 宠物分类 */
    @Excel(name = "宠物分类")
    private String petClass;

    /** 宠物分类ID */
    private String petClassId;

    /** 中文首字母 */
    @Excel(name = "中文首字母")
    private String cnInitials;

    /** 宠物品种ID */
    private String petBreedId;

    /** 宠物品种 */
    @Excel(name = "宠物品种")
    private String petBreed;

    /** 英文首字母 */
    @Excel(name = "英文首字母")
    private String enInitials;

    /** 宠物品种（英文） */
    @Excel(name = "宠物品种（英文）")
    private String petBreedEn;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 最后更新人 */
    @Excel(name = "最后更新人")
    private String lastUpdater;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCnNo(String cnNo)
    {
        this.cnNo = cnNo;
    }

    public String getCnNo()
    {
        return cnNo;
    }

    public void setEnNo(String enNo)
    {
        this.enNo = enNo;
    }

    public String getEnNo()
    {
        return enNo;
    }

    public void setPetClass(String petClass)
    {
        this.petClass = petClass;
    }

    public String getPetClass()
    {
        return petClass;
    }

    public void setPetClassId(String petClassId)
    {
        this.petClassId = petClassId;
    }

    public String getPetClassId()
    {
        return petClassId;
    }

    public void setCnInitials(String cnInitials)
    {
        this.cnInitials = cnInitials;
    }

    public String getCnInitials()
    {
        return cnInitials;
    }

    public void setPetBreedId(String petBreedId)
    {
        this.petBreedId = petBreedId;
    }

    public String getPetBreedId()
    {
        return petBreedId;
    }

    public void setPetBreed(String petBreed)
    {
        this.petBreed = petBreed;
    }

    public String getPetBreed()
    {
        return petBreed;
    }

    public void setEnInitials(String enInitials)
    {
        this.enInitials = enInitials;
    }

    public String getEnInitials()
    {
        return enInitials;
    }

    public void setPetBreedEn(String petBreedEn)
    {
        this.petBreedEn = petBreedEn;
    }

    public String getPetBreedEn()
    {
        return petBreedEn;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setLastUpdater(String lastUpdater)
    {
        this.lastUpdater = lastUpdater;
    }

    public String getLastUpdater()
    {
        return lastUpdater;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("cnNo", getCnNo())
            .append("enNo", getEnNo())
            .append("petClass", getPetClass())
            .append("petClassId", getPetClassId())
            .append("cnInitials", getCnInitials())
            .append("petBreedId", getPetBreedId())
            .append("petBreed", getPetBreed())
            .append("enInitials", getEnInitials())
            .append("petBreedEn", getPetBreedEn())
            .append("status", getStatus())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("creator", getCreator())
            .append("lastUpdater", getLastUpdater())
            .toString();
    }
}
