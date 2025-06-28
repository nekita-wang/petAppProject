package com.petlife.platform.app.auth.enums;


import com.petlife.platform.app.core.api.ServiceCode;

public enum AuthExceptionCode implements ServiceCode {

    // 登录相关错误
    ACCOUNT_NOT_EXIST(1000, "该手机号未注册，请先注册"),
    PASSWORD_INCORRECT(1001, "密码错误，请重新输入"),
    ACCOUNT_LOCKED(1002, "密码错误次数过多，账号已锁定，请30分钟后重试"),
    ACCOUNT_FROZEN(1003, "该账号已被冻结，无法登录"),
    ACCOUNT_CANCELLED(1004, "该账号已注销"),
    ACCOUNT_DISABLED(1005, "账号被禁用"),
    LOGIN_TOO_FREQUENT(1006, "登录过于频繁，请稍后再试"),

    // 认证流程相关错误
    GRANTER_INEXISTENCE(1010, "grantType类型不存在"),
    SELECT_ERROR(1011, "查询失败"),

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