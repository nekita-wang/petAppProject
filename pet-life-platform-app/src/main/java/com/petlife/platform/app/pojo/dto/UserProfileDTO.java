package com.petlife.platform.app.pojo.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Long userId;
    private String nickname;
    private String password;
    private String avatar;
    private Byte gender;
    private String birthday;
}
