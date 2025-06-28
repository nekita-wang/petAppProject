package com.petlife.platform.petTypes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petlife.platform.common.annotation.Excel;
import com.petlife.platform.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 宠物分类对象 pet_classification
 *
 * @author ruoyi
 * @date 2025-06-27
 */
public class PetClassification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private String no;

    /** 宠物分类ID */
    private String petClassId;

    /** 宠物分类 */
    @Excel(name = "宠物分类")
    private String petClass;

    /** 宠物分类（英文） */
    @Excel(name = "宠物分类", readConverterExp = "英=文")
    private String petClassEn;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creator;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /** 最后更新人 */
    @Excel(name = "最后更新人")
    private String lastUpdater;

    public void setNo(String no)
    {
        this.no = no;
    }

    public String getNo()
    {
        return no;
    }

    public void setPetClassId(String petClassId)
    {
        this.petClassId = petClassId;
    }

    public String getPetClassId()
    {
        return petClassId;
    }

    public void setPetClass(String petClass)
    {
        this.petClass = petClass;
    }

    public String getPetClass()
    {
        return petClass;
    }

    public void setPetClassEn(String petClassEn)
    {
        this.petClassEn = petClassEn;
    }

    public String getPetClassEn()
    {
        return petClassEn;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
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
            .append("no", getNo())
            .append("petClassId", getPetClassId())
            .append("petClass", getPetClass())
            .append("petClassEn", getPetClassEn())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("lastUpdater", getLastUpdater())
            .toString();
    }
}
