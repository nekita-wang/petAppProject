package com.petlife.platform.app.service;

import com.petlife.platform.common.pojo.dto.PetBreedQuery;
import com.petlife.platform.common.pojo.dto.PetDTO;
import com.petlife.platform.common.pojo.dto.PetInfoQuery;
import com.petlife.platform.common.pojo.entity.PetInfo;
import com.petlife.platform.common.pojo.vo.PetClassVo;

import java.util.List;
import java.util.Map;

public interface PetService {
    void addPet(PetDTO dto);
    boolean isPetNicknameExist(Long userId, String nickname);

    void addPetInfo(PetInfoQuery petInfoQuery);

    Map<String, Object> selectPetBreedAppList(PetBreedQuery petBreedQuery);

    List<PetClassVo> selectPetClass();
}
