package com.petlife.platform.petTypes.mapper;

import com.petlife.platform.petTypes.domain.PetClassVo;
import com.petlife.platform.petTypes.domain.PetClassification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宠物分类Mapper接口
 *
 * @author ruoyi
 * @date 2025-06-27
 */
public interface PetClassificationMapper
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
     * 删除宠物分类
     *
     * @param petClassId 宠物分类主键
     * @return 结果
     */
    public int deletePetClassificationByPetClassId(String petClassId);

    /**
     * 批量删除宠物分类
     *
     * @param petClassIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePetClassificationByPetClassIds(String[] petClassIds);

    /**
     * 查询宠物类别
     * @param status
     * @return
     */
    public  List<PetClassVo> selectPetClass(@Param("status") Integer status);

    public PetClassVo selectPetClassId(String petClass);

    /**
     * 修改状态失效
     * @param ids
     * @return
     */
    public int petClassificationByPetClassIds(List<String> ids);
}
