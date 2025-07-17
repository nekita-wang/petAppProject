package com.petlife.platform.app.service;

import com.petlife.platform.common.pojo.dto.PetBreedQuery;
import com.petlife.platform.common.pojo.dto.PetInfoDTO;
import com.petlife.platform.common.pojo.vo.PetClassVo;

import java.util.List;
import java.util.Map;

public interface PetService {
    boolean isPetNicknameExist(Long userId, String nickname);

    void addPetInfo(PetInfoDTO petInfoDTO,Long userId);

    Map<String, Object> selectPetBreedAppList(PetBreedQuery petBreedQuery);

    List<PetClassVo> selectPetClass();
}
