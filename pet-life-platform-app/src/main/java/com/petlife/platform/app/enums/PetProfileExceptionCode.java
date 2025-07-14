package com.petlife.platform.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 宠物资料相关异常枚举
 */
@Getter
@AllArgsConstructor
public enum PetProfileExceptionCode {
    
    /**
     * 宠物昵称已存在
     */
    PET_NICKNAME_EXIST(4001, "宠物昵称已存在，请更换"),
    
    /**
     * 已填写过宠物信息
     */
    PET_INFO_ALREADY_EXISTS(4002, "您已填写过宠物信息，无需重复填写"),
    
    /**
     * 宠物信息插入失败
     */
    PET_INFO_INSERT_FAILED(4003, "插入宠物信息失败，未影响任何行"),
    
    /**
     * 宠物信息填写异常
     */
    PET_INFO_FILL_EXCEPTION(4004, "填写宠物信息异常");

    private final int code;
    private final String message;
}
