package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isNicknameExist(Long userId, String nickname) {
        // 修改为排除自己
        return userMapper.countByNicknameExcludeUser(userId, nickname) > 0;
    }
}