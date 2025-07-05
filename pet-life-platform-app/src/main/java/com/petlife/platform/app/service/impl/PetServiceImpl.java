package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.mapper.PetMapper;
import com.petlife.platform.common.pojo.dto.PetDTO;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetMapper petMapper;

    @Override
    public void addPet(PetDTO dto) {
        if (!StringUtils.hasText(dto.getNickname()) || dto.getType() == null) {
            throw new PetException(AuthExceptionCode.PARAMS_MISSING);
        }
        if (isPetNicknameExist(dto.getUserId(), dto.getNickname())) {
            throw new PetException(AuthExceptionCode.PET_NICKNAME_EXIST);
        }
        // 保存
        petMapper.insertPet(dto);
    }

    @Override
    public boolean isPetNicknameExist(Long userId, String nickname) {
        return petMapper.countByUserIdAndNickname(userId, nickname) > 0;
    }
}
