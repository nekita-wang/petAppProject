package com.petlife.platform.app.auth.enums;

import com.petlife.platform.common.core.api.ServiceCode;

public enum AuthExceptionCode implements ServiceCode {

    // 登录相关错误
    ACCOUNT_NOT_EXIST(1000, "该手机号未注册，请先注册"),
    PASSWORD_INCORRECT(1001, "密码错误，请重新输入"),
    ACCOUNT_LOCKED(1002, "密码错误次数过多，账号已临时锁定30分钟，请稍后再试"),
    ACCOUNT_FROZEN(1003, "该账号已被冻结，无法登录"),
    ACCOUNT_CANCELLED(1004, "该账号已注销"),
    ACCOUNT_DISABLED(1005, "账号被禁用"),
    LOGIN_TOO_FREQUENT(1006, "登录过于频繁，请稍后再试"),

    PHONE_FORMAT_ERROR(1007, "手机号格式不正确"),
    PHONE_IS_EMPTY(1008, "手机号不能为空"),
    PASSWORD_IS_EMPTY(1009, "密码不能为空"),
    USER_NICKNAME_EXIST(1022, "用户昵称已存在，请更换"),

    // 验证码相关
    CODE_IS_EMPTY(1014, "验证码不能为空"),
    CODE_EXPIRED(1015, "验证码已过期或未发送"),
    CODE_INCORRECT(1016, "验证码不正确，请重新输入"),
    CODE_SEND_TOO_FREQUENT(1017, "验证码发送过于频繁"),
    CODE_SEND_FAILED(1018, "验证码发送失败，请稍后重试"),

    // 认证流程相关错误
    GRANTER_INEXISTENCE(1010, "grantType类型不存在"),
    SELECT_ERROR(1011, "查询失败"),

    PET_NICKNAME_EXIST(1012, "宠物昵称已存在，请更换"),

    PARAMS_MISSING(1013, "缺少必要参数"),

    PASSWORD_DECRYPT_ERROR(1014, "密码解密失败"),
    PASSWORD_TOO_WEAK(1019, "密码强度不足"),

    // 注册相关错误
    ACCOUNT_ALREADY_EXISTS(1020, "该手机号已注册"),
    REGISTER_FAILED(1021, "注册失败，请稍后重试"),

    // 通用/系统错误
    INTERNAL_ERROR(1999, "系统内部错误，请联系管理员");

    private final int code;
    private final String msg;

    AuthExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
