package com.petlife.platform.petTypes.service.impl;

import com.petlife.platform.common.utils.DateUtils;
import com.petlife.platform.petTypes.domain.PetClassVo;
import com.petlife.platform.petTypes.domain.PetClassification;
import com.petlife.platform.petTypes.mapper.PetClassificationMapper;
import com.petlife.platform.petTypes.service.IPetClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petlife.platform.common.utils.SecurityUtils.getUsername;

/**
 * 宠物分类Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-27
 */
@Service
public class PetClassificationServiceImpl implements IPetClassificationService
{
    @Autowired
    private PetClassificationMapper petClassificationMapper;

    /**
     * 查询宠物分类
     *
     * @param petClassId 宠物分类主键
     * @return 宠物分类
     */
    @Override
    public PetClassification selectPetClassificationByPetClassId(String petClassId)
    {
        return petClassificationMapper.selectPetClassificationByPetClassId(petClassId);
    }

    /**
     * 查询宠物分类列表
     *
     * @param petClassification 宠物分类
     * @return 宠物分类
     */
    @Override
    public List<PetClassification> selectPetClassificationList(PetClassification petClassification)
    {
        return petClassificationMapper.selectPetClassificationList(petClassification);
    }

    @Override
    public List<PetClassVo> selectPetClass(Boolean state) {

        List<PetClassVo> result=null;
        if (state){
            int status=0;
            result = petClassificationMapper.selectPetClass(status);
        }else {
            result =  petClassificationMapper.selectPetClass(null);
        }
        return result;
    }

    /**
     * 新增宠物分类
     *
     * @param petClassification 宠物分类
     * @return 结果
     */
    @Override
    public int insertPetClassification(PetClassification petClassification)
    {

        petClassification.setCreateTime(DateUtils.getNowDate());
        petClassification.setLastUpdateTime(DateUtils.getNowDate());
        petClassification.setStatus(0);
        petClassification.setCreator(getUsername());
        petClassification.setLastUpdater(getUsername());
        return petClassificationMapper.insertPetClassification(petClassification);
    }

    /**
     * 修改宠物分类
     *
     * @param petClassification 宠物分类
     * @return 结果
     */
    @Override
    public int updatePetClassification(PetClassification petClassification)
    {
        petClassification.setLastUpdateTime(DateUtils.getNowDate());
        petClassification.setLastUpdater(getUsername());
        return petClassificationMapper.updatePetClassification(petClassification);
    }

    /**
     * 批量删除宠物分类
     *
     * @param petClassIds 需要删除的宠物分类主键
     * @return 结果
     */
    @Override
    public int deletePetClassificationByPetClassIds(String[] petClassIds)
    {
        return petClassificationMapper.deletePetClassificationByPetClassIds(petClassIds);
    }

    /**
     * 删除宠物分类信息
     *
     * @param petClassId 宠物分类主键
     * @return 结果
     */
    @Override
    public int deletePetClassificationByPetClassId(String petClassId)
    {
        return petClassificationMapper.deletePetClassificationByPetClassId(petClassId);
    }

    @Override
    public int petClassificationByPetClassIds(List<String> ids) {
        return petClassificationMapper.petClassificationByPetClassIds(ids);
    }
}
