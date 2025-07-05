package com.petlife.platform.app.service;

import com.petlife.platform.common.pojo.dto.PetDTO;

public interface PetService {
    void addPet(PetDTO dto);
    boolean isPetNicknameExist(Long userId, String nickname);
}
