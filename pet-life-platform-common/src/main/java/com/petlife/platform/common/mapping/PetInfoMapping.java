package com.petlife.platform.common.mapping;

import com.petlife.platform.common.pojo.dto.PetInfoDTO;
import com.petlife.platform.common.pojo.entity.PetInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetInfoMapping {

    PetInfo petInfoQueryToPetInfo(PetInfoDTO petInfoDTO);
}
