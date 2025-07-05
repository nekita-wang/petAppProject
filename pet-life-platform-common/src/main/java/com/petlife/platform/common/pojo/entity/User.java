package com.petlife.platform.common.pojo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户ID，自增主键
     */
    private Long userId;

    /**
     * 手机号（+86前缀+11位数字）
     */
    private String phone;

    /**
     * 用户昵称（中文/字母/数字，≤10字符）
     */
    private String nickName;

    /**
     * 登录密码（使用 bcrypt 加密存储）
     */
    private String password;

    /**
     * 性别（0=未设置，1=男，2=女）
     */
    private Byte gender;

    /**
     * 出生日期（YYYY-MM-DD）
     */
    private LocalDate birthday;

    /**
     * 用户头像图片URL（≤10MB）
     */
    private String avatarUrl;

    /**
     * 注册时间（用户注册时自动生成）
     */
    private LocalDateTime registerTime;

    /**
     * 最近一次登录时间（每次登录时自动更新）
     */
    private LocalDateTime lastLoginTime;

    /**
     * 是否未成年人（0=成年，1=未成年）
     */
    private Byte minor;

    /**
     * 实名认证状态（0=未认证，1=已认证）
     */
    private Byte auth;

    /**
     * 账号状态（0=生效，1=已注销，2=冻结）
     */
    private Byte status;

    /**
     * 最近离线时间（用户退出登录或下线时自动更新）
     */
    private LocalDateTime lastLogoffTime;

    /**
     * 用户个性签名（最长100个汉字/300字符）
     */
    private String personalSignature;
}
