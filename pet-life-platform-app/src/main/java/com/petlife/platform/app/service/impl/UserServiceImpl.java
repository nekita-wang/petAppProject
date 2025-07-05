package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.common.enums.UserProfileExceptionCode;
import com.petlife.platform.common.pojo.dto.UserProfileDTO;
import com.petlife.platform.app.service.UserService;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.utils.SecurityUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.common.utils.sign.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void completeProfile(UserProfileDTO dto) {
        // 性别范围校验
        if (dto.getGender() == null || dto.getGender() < 0 || dto.getGender() > 2) {
            throw new PetException(UserProfileExceptionCode.GENDER_INVALID.getCode(), UserProfileExceptionCode.GENDER_INVALID.getMessage());
        }

        // 生日格式校验
        if (!StringUtils.hasText(dto.getBirthday())) {
            throw new PetException(UserProfileExceptionCode.BIRTHDAY_EMPTY.getCode(), UserProfileExceptionCode.BIRTHDAY_EMPTY.getMessage());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(dto.getBirthday());
            } catch (Exception e) {
                throw new PetException(UserProfileExceptionCode.BIRTHDAY_FORMAT_ERROR.getCode(), UserProfileExceptionCode.BIRTHDAY_FORMAT_ERROR.getMessage());
            }
        }

        // 昵称唯一性（排除自己）
        if (isNicknameExist(dto.getUserId(), dto.getNickname())) {
            throw new PetException(UserProfileExceptionCode.NICKNAME_EXIST.getCode(), UserProfileExceptionCode.NICKNAME_EXIST.getMessage());
        }

        // 解密前端 RSA 密文
        String rawPassword;
        try {
            rawPassword = RsaUtils.decryptByPrivateKey(RsaUtils.getPrivateKey(), dto.getPassword());
        } catch (Exception e) {
            throw new PetException(AuthExceptionCode.PASSWORD_DECRYPT_ERROR);
        }

        // 对明文密码做哈希
        String hashedPassword = SecurityUtils.encryptPassword(rawPassword);
        dto.setPassword(hashedPassword);

        // 更新用户资料
        try {
            userMapper.updateProfile(dto);
        } catch (DuplicateKeyException e) {
            // 捕获数据库唯一键冲突
            throw new PetException(UserProfileExceptionCode.NICKNAME_EXIST.getCode(), UserProfileExceptionCode.NICKNAME_EXIST.getMessage());
        }
    }

    @Override
    public boolean isNicknameExist(Long userId, String nickname) {
        // 修改为排除自己
        return userMapper.countByNickname(userId, nickname) > 0;
    }
}