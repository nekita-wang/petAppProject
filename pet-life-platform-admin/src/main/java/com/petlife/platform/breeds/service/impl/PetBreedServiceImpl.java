package com.petlife.platform.breeds.service.impl;

import com.petlife.platform.breeds.domain.PetBreed;
import com.petlife.platform.breeds.mapper.PetBreedMapper;
import com.petlife.platform.breeds.service.IPetBreedService;
import com.petlife.platform.common.utils.DateUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.petTypes.domain.PetClassVo;
import com.petlife.platform.petTypes.mapper.PetClassificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.petlife.platform.common.utils.SecurityUtils.getUsername;

/**
 * 宠物品种Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-27
 */
@Service
public class PetBreedServiceImpl implements IPetBreedService
{
    @Autowired
    private PetBreedMapper petBreedMapper;

    @Autowired
    private PetClassificationMapper petClassificationMapper;
    /**
     * 查询宠物品种
     *
     * @param petBreedId 宠物品种主键
     * @return 宠物品种
     */
    @Override
    public PetBreed selectPetBreedByPetClassId(String petBreedId)
    {
        return petBreedMapper.selectPetBreedByPetClassId(petBreedId);
    }



    /**
     * 查询宠物品种列表
     *
     * @param petBreed 宠物品种
     * @return 宠物品种
     */
    @Override
    public List<PetBreed> selectPetBreedList(PetBreed petBreed)
    {
        return petBreedMapper.selectPetBreedList(petBreed);
    }

    /**
     * 新增宠物品种
     *
     * @param petBreed 宠物品种
     * @return 结果
     */
    @Override
    public int insertPetBreed(PetBreed petBreed)
    {

//        更新时间和作者
        petBreed.setCreateTime(DateUtils.getNowDate());
        petBreed.setCreator(getUsername());
        petBreed.setLastUpdateTime(DateUtils.getNowDate());
        petBreed.setLastUpdater(getUsername());

        //状态为生效
        petBreed.setStatus(0);

        //查询宠物分类插入id
        PetClassVo petClassVo = petClassificationMapper.selectPetClassId(petBreed.getPetClass());
        petBreed.setPetClassId(petClassVo.getPetClassId());

        //自动生成breedId   CART_BREED_001
        String breedId = petClassVo.getPetClassEn().toUpperCase() + "_BREED_";
        String breedIdMax = petBreedMapper.selectBreedIdMax(breedId);

        //初始值
        int nextNum = 1;

        //不是空值则数字加一
        if (StringUtils.isNotBlank(breedIdMax)) {
            String numPart = breedIdMax.substring(breedId.length());
            nextNum = Integer.parseInt(numPart) + 1;
        }

        //数字前面添加3个0
        String newId = breedId + String.format("%03d", nextNum);
        petBreed.setPetBreedId(newId);
        return petBreedMapper.insertPetBreed(petBreed);
    }

    /**
     * 修改宠物品种
     *
     * @param petBreed 宠物品种
     * @return 结果
     */
    @Override
    public int updatePetBreed(PetBreed petBreed)
    {
        petBreed.setLastUpdateTime(DateUtils.getNowDate());
        petBreed.setLastUpdater(getUsername());
        PetClassVo petClassVo = petClassificationMapper.selectPetClassId(petBreed.getPetClass());
        petBreed.setPetClassId(petClassVo.getPetClassId());
        System.out.println(petBreed.toString());
        return petBreedMapper.updatePetBreed(petBreed);
    }

    /**
     * 批量删除宠物品种
     *
     * @param petClassIds 需要删除的宠物品种主键
     * @return 结果
     */
    @Override
    public int deletePetBreedByPetClassIds(String[] petClassIds)
    {
        return petBreedMapper.deletePetBreedByPetClassIds(petClassIds);
    }

    /**
     * 删除宠物品种信息
     *
     * @param petClassId 宠物品种主键
     * @return 结果
     */
    @Override
    public int deletePetBreedByPetClassId(String petClassId)
    {
        return petBreedMapper.deletePetBreedByPetClassId(petClassId);
    }

    @Override
    public int updateBreedStatus(List<String> ids, Integer status) {
        return petBreedMapper.updateBreedStatus(ids, status);
    }
}
