package com.petlife.platform.app.pojo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String phone;
    private String nickName;
    private String password;
    private Byte gender;
    private LocalDate birthday;
    private String avatarUrl;
    private LocalDateTime registerTime;
    private LocalDateTime lastLoginTime;
    private Byte minor;
    private Byte auth;
    private Byte status;
}
