package com.petlife.platform.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 完善个人资料相关错误码和信息
 */
@Getter
@AllArgsConstructor
public enum UserProfileExceptionCode {

    BIRTHDAY_EMPTY(40001, "生日不能为空"),
    BIRTHDAY_FORMAT_ERROR(40002, "生日格式错误，应为 yyyy-MM-dd"),
    GENDER_INVALID(40003, "性别非法"),
    NICKNAME_EXIST(40004, "昵称已存在"),
    PASSWORD_DECRYPT_ERROR(40005, "密码解密失败");

    private final int code;
    private final String message;
}