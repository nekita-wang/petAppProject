package com.petlife.platform.app.service;

import com.petlife.platform.common.pojo.dto.UserProfileDTO;

public interface UserService {
    void completeProfile(UserProfileDTO dto);
    boolean isNicknameExist(Long userId, String nickname);
}
