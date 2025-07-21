package com.petlife.platform.breeds.service;

import com.petlife.platform.breeds.domain.PetBreed;

import java.util.List;
import java.util.Map;

/**
 * 宠物品种Service接口
 *
 * @author ruoyi
 * @date 2025-06-27
 */
public interface IPetBreedService
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
     * 批量删除宠物品种
     *
     * @param petClassIds 需要删除的宠物品种主键集合
     * @return 结果
     */
    public int deletePetBreedByPetClassIds(String[] petClassIds);

    /**
     * 删除宠物品种信息
     *
     * @param petClassId 宠物品种主键
     * @return 结果
     */
    public int deletePetBreedByPetClassId(String petClassId);

    public int updateBreedStatus(List<String> ids, Integer status);
}
