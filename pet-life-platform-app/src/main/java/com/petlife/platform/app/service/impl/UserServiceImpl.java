package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.app.pojo.dto.UserProfileDTO;
import com.petlife.platform.app.service.UserService;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void completeProfile(UserProfileDTO dto) {
        // 必填项校验
        if (!StringUtils.hasText(dto.getNickname()) || !StringUtils.hasText(dto.getPassword())) {
            throw new PetException(AuthExceptionCode.PHONE_IS_EMPTY);
        }
        // 密码强度示例（略）
        // 昵称唯一性
        if (isNicknameExist(dto.getUserId(), dto.getNickname())) {
            throw new PetException(AuthExceptionCode.USER_NICKNAME_EXIST);
        }
        // TODO 密码加密
//        String saltPwd = PasswordUtil.encode(dto.getPassword());
        String saltPwd = "1111";
        // 更新用户资料
        userMapper.updateProfile(dto.getUserId(), dto.getNickname(), saltPwd, dto.getAvatar(), dto.getGender(), dto.getBirthday());
    }

    @Override
    public boolean isNicknameExist(Long userId, String nickname) {
        return userMapper.countByNickname(userId, nickname) > 0;
    }
}
