package com.petlife.platform.breeds.mapper;

import com.petlife.platform.breeds.domain.PetBreed;
import com.petlife.platform.breeds.domain.PetBreedQuery;
import com.petlife.platform.breeds.domain.PetBreedVo;

import java.util.List;

/**
 * 宠物品种Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-27
 */
public interface PetBreedMapper
{
    /**
     * 查询宠物品种
     *
     * @param petBreedId 宠物品种主键
     * @return 宠物品种
     */
    public PetBreed selectPetBreedByPetClassId(String petBreedId);

    /**
     * 查询宠物品种列表
     *
     * @param petBreed 宠物品种
     * @return 宠物品种集合
     */
    public List<PetBreed> selectPetBreedList(PetBreed petBreed);

    /**
     * 新增宠物品种
     *
     * @param petBreed 宠物品种
     * @return 结果
     */
    public int insertPetBreed(PetBreed petBreed);

    /**
     * 修改宠物品种
     *
     * @param petBreed 宠物品种
     * @return 结果
     */
    public int updatePetBreed(PetBreed petBreed);

    /**
     * 删除宠物品种
     *
     * @param petClassId 宠物品种主键
     * @return 结果
     */
    public int deletePetBreedByPetClassId(String petClassId);

    /**
     * 批量删除宠物品种
     *
     * @param petClassIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePetBreedByPetClassIds(String[] petClassIds);

    public int updateBreedStatus(List<String> ids);

    List<PetBreedVo> selectPetBreedAppList(PetBreedQuery petBreedQuery);

    List<String> selectHot(String petClass);

    String selectBreedIdMax(String breedId);
}
