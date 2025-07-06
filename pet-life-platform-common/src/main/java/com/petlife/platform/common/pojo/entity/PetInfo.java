package com.petlife.platform.common.pojo.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.petlife.platform.common.annotation.Excel;
import com.petlife.platform.common.core.domain.BaseEntity;

/**
 * 宠物信息对象 pet_info
 *
 * @author ruoyi
 * @date 2025-07-06
 */
public class PetInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 宠物 ID */
    private String PetID;

    /** 用户 ID */
    @Excel(name = "用户 ID")
    private String UserID;

    /** 宠物昵称（唯一） */
    @Excel(name = "宠物昵称", readConverterExp = "唯=一")
    private String PetNickName;

    /** 宠物分类 */
    @Excel(name = "宠物分类")
    private String PetClass;

    /** 宠物品种 */
    @Excel(name = "宠物品种")
    private String PetBreed;

    /** 宠物性别(0=未设置，1=公，2=母) */
    @Excel(name = "宠物性别(0=未设置，1=公，2=母)")
    private Long PetGender;

    /** 是否绝育 (0=否，1=是) */
    @Excel(name = "是否绝育 (0=否，1=是)")
    private Long Sterilized;

    /** 宠物生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "宠物生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date PetBirthday;

    /** 领养/购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领养/购买日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date AdoptionDate;

    /** 宠物头像 */
    @Excel(name = "宠物头像")
    private String PetAvatarURL;

    /** 状态 (0=生效，1=已死亡，2=已注销) */
    @Excel(name = "状态 (0=生效，1=已死亡，2=已注销)")
    private Long Status;

    public void setPetID(String PetID)
    {
        this.PetID = PetID;
    }

    public String getPetID()
    {
        return PetID;
    }

    public void setUserID(String UserID)
    {
        this.UserID = UserID;
    }

    public String getUserID()
    {
        return UserID;
    }

    public void setPetNickName(String PetNickName)
    {
        this.PetNickName = PetNickName;
    }

    public String getPetNickName()
    {
        return PetNickName;
    }

    public void setPetClass(String PetClass)
    {
        this.PetClass = PetClass;
    }

    public String getPetClass()
    {
        return PetClass;
    }

    public void setPetBreed(String PetBreed)
    {
        this.PetBreed = PetBreed;
    }

    public String getPetBreed()
    {
        return PetBreed;
    }

    public void setPetGender(Long PetGender)
    {
        this.PetGender = PetGender;
    }

    public Long getPetGender()
    {
        return PetGender;
    }

    public void setSterilized(Long Sterilized)
    {
        this.Sterilized = Sterilized;
    }

    public Long getSterilized()
    {
        return Sterilized;
    }

    public void setPetBirthday(Date PetBirthday)
    {
        this.PetBirthday = PetBirthday;
    }

    public Date getPetBirthday()
    {
        return PetBirthday;
    }

    public void setAdoptionDate(Date AdoptionDate)
    {
        this.AdoptionDate = AdoptionDate;
    }

    public Date getAdoptionDate()
    {
        return AdoptionDate;
    }

    public void setPetAvatarURL(String PetAvatarURL)
    {
        this.PetAvatarURL = PetAvatarURL;
    }

    public String getPetAvatarURL()
    {
        return PetAvatarURL;
    }

    public void setStatus(Long Status)
    {
        this.Status = Status;
    }

    public Long getStatus()
    {
        return Status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("PetID", getPetID())
            .append("UserID", getUserID())
            .append("PetNickName", getPetNickName())
            .append("PetClass", getPetClass())
            .append("PetBreed", getPetBreed())
            .append("PetGender", getPetGender())
            .append("Sterilized", getSterilized())
            .append("PetBirthday", getPetBirthday())
            .append("AdoptionDate", getAdoptionDate())
            .append("PetAvatarURL", getPetAvatarURL())
            .append("Status", getStatus())
            .toString();
    }
}
