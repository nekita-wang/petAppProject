package com.petlife.platform.common.mapping;

import com.petlife.platform.common.pojo.dto.PetInfoQuery;
import com.petlife.platform.common.pojo.entity.PetInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetInfoMapping {

    PetInfo petInfoQueryToPetInfo(PetInfoQuery petInfoQuery);
}
