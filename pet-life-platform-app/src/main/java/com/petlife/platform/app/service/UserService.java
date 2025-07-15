package com.petlife.platform.app.service;

public interface UserService {
    boolean isNicknameExist(Long userId, String nickname);
}
