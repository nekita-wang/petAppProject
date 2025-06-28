package com.petlife.platform.petTypes.service;

import com.petlife.platform.petTypes.domain.PetClassVo;
import com.petlife.platform.petTypes.domain.PetClassification;

import java.util.List;

/**
 * 宠物分类Service接口
 *
 * @author ruoyi
 * @date 2025-06-27
 */
public interface IPetClassificationService
{
    /**
     * 查询宠物分类
     *
     * @param petClassId 宠物分类主键
     * @return 宠物分类
     */
    public PetClassification selectPetClassificationByPetClassId(String petClassId);

    /**
     * 查询宠物分类列表
     *
     * @param petClassification 宠物分类
     * @return 宠物分类集合
     */
    public List<PetClassification> selectPetClassificationList(PetClassification petClassification);

    /**
     * 查询宠物类别
     *
     * @return 宠物分类集合
     */
    public List<PetClassVo> selectPetClass();

    /**
     * 新增宠物分类
     *
     * @param petClassification 宠物分类
     * @return 结果
     */
    public int insertPetClassification(PetClassification petClassification);

    /**
     * 修改宠物分类
     *
     * @param petClassification 宠物分类
     * @return 结果
     */
    public int updatePetClassification(PetClassification petClassification);

    /**
     * 批量删除宠物分类
     *
     * @param petClassIds 需要删除的宠物分类主键集合
     * @return 结果
     */
    public int deletePetClassificationByPetClassIds(String[] petClassIds);

    /**
     * 删除宠物分类信息
     *
     * @param petClassId 宠物分类主键
     * @return 结果
     */
    public int deletePetClassificationByPetClassId(String petClassId);

    public int petClassificationByPetClassIds(List<String> ids);
}
