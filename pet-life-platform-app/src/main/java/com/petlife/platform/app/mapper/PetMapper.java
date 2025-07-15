package com.petlife.platform.app.mapper;

import com.petlife.platform.common.pojo.dto.PetBreedQuery;
import com.petlife.platform.common.pojo.entity.PetInfo;
import com.petlife.platform.common.pojo.vo.PetBreedVo;
import com.petlife.platform.common.pojo.vo.PetClassVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetMapper {
    int countByUserIdAndNickname(@Param("userId") Long userId,@Param("nickname")  String nickname);


    /**
     * 获取宠物品种列表
     * @param petBreedQuery
     * @return
     */
    List<PetBreedVo> selectPetBreedAppList(PetBreedQuery petBreedQuery);

    /**
     * 添加宠物信息
     * @param petInfo
     */
    int insertPetInfo(PetInfo petInfo);

    /**
     * 获取热门列表
     * @param petClass
     * @return
     */
    List<String> selectHot( String petClass);

    /**
     * 宠物类别查询
     * @return
     */
    List<PetClassVo> selectPetClass();

    int countByUserId(Long userId);
}
