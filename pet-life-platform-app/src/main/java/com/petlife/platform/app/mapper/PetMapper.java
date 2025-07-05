package com.petlife.platform.app.mapper;

import com.petlife.platform.common.pojo.dto.PetDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PetMapper {
    int countByUserIdAndNickname(Long userId, String nickname);

    void insertPet(PetDTO dto);
}
