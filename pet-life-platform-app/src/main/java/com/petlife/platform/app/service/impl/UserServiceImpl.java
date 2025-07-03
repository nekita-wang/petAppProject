package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.app.pojo.dto.UserProfileDTO;
import com.petlife.platform.app.service.UserService;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.utils.SecurityUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.common.utils.sign.RsaUtils;
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
        // 昵称唯一性
        if (isNicknameExist(dto.getUserId(), dto.getNickname())) {
            throw new PetException(AuthExceptionCode.USER_NICKNAME_EXIST);
        }

        // 解密：用后端私钥解密前端传来的密文密码
        String rawPassword;
        try {
            rawPassword = RsaUtils.decryptByPrivateKey(RsaUtils.getPrivateKey(), dto.getPassword());
        } catch (Exception e) {
            throw new PetException(AuthExceptionCode.PASSWORD_DECRYPT_ERROR);
        }

        // 对明文密码做哈希：得到真正要存数据库的密文
        String hashedPassword = SecurityUtils.encryptPassword(rawPassword);

        // 更新用户资料
        userMapper.updateProfile(dto.getUserId(), dto.getNickname(), hashedPassword,
                dto.getAvatar(), dto.getGender(), dto.getBirthday());
    }

    @Override
    public boolean isNicknameExist(Long userId, String nickname) {
        return userMapper.countByNickname(userId, nickname) > 0;
    }
}
