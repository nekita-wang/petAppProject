package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.mapper.PetMapper;
import com.petlife.platform.app.enums.PetProfileExceptionCode;
import com.petlife.platform.common.mapping.PetInfoMapping;
import com.petlife.platform.common.pojo.dto.PetBreedQuery;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.pojo.dto.PetInfoDTO;
import com.petlife.platform.common.pojo.entity.PetInfo;
import com.petlife.platform.common.pojo.vo.PetBreedListVo;
import com.petlife.platform.common.pojo.vo.PetBreedVo;
import com.petlife.platform.common.pojo.vo.PetClassVo;
import com.petlife.platform.common.utils.DateValidationUtils;
import com.petlife.platform.common.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.petlife.platform.common.utils.SecurityUtils.getUserId;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private PetInfoMapping petInfoMapping;


    @Override
    public boolean isPetNicknameExist(Long userId, String nickname) {
        return petMapper.countByUserIdAndNickname(userId, nickname) > 0;
    }

    @Override
    public void addPetInfo(PetInfoDTO petInfoDTO, Long userId) {
        // 直接使用传入的 userId，不再从 DTO 取
        // 检查是否已有宠物信息（仅在非注册时检查）
        if (petMapper.countByUserId(userId) > 0) {
            throw new PetException(PetProfileExceptionCode.PET_INFO_ALREADY_EXISTS.getCode(),
                    PetProfileExceptionCode.PET_INFO_ALREADY_EXISTS.getMessage());
        }

        DateValidationUtils.validatePetDateLogic(petInfoDTO.getPetBirthday(),
                petInfoDTO.getAdoptionDate(), "到家日期早于生日");

        // 填写日期不能小于当天日期
        LocalDate now = LocalDate.now();

        DateValidationUtils.validatePetDateLogic(petInfoDTO.getPetBirthday(),
                now, "宠物生日不能晚于当前时间");

        DateValidationUtils.validatePetDateLogic(petInfoDTO.getAdoptionDate(),
                now, "到家日期不能晚于当前时间");


        // 处理头像路径，只保留 /profile 后面的内容
        String avatarUrl = petInfoDTO.getPetAvatarURL();
        if (avatarUrl != null) {
            int idx = avatarUrl.indexOf("/profile");
            if (idx != -1) {
                avatarUrl = avatarUrl.substring(idx); // 只保留 /profile 开头及后面
            }
        }

        // 使用 MapStruct 进行转换
        PetInfo info = petInfoMapping.petInfoQueryToPetInfo(petInfoDTO);
        info.setUserId(userId);
        info.setStatus(0L);
        info.setPetAvatarURL(avatarUrl); // 覆盖为相对路径

        // 判断是否唯一，不唯一抛异常
        if (isPetNicknameExist(userId, info.getPetNickName())) {
            throw new PetException(PetProfileExceptionCode.PET_NICKNAME_EXIST.getCode(), 
                                 PetProfileExceptionCode.PET_NICKNAME_EXIST.getMessage());
        }

        // 插入数据
        try {
            int insertPetInfo = petMapper.insertPetInfo(info);
            if (insertPetInfo <= 0) {
                throw new PetException(PetProfileExceptionCode.PET_INFO_INSERT_FAILED.getCode(), 
                                     PetProfileExceptionCode.PET_INFO_INSERT_FAILED.getMessage());
            }
        } catch (Exception e) {
            throw new PetException(PetProfileExceptionCode.PET_INFO_FILL_EXCEPTION.getCode(), 
                                 PetProfileExceptionCode.PET_INFO_FILL_EXCEPTION.getMessage());
        }
    }

    @Override
    public Map<String, Object> selectPetBreedAppList(PetBreedQuery petBreedQuery) {
        // 获取宠物品种列表
        List<PetBreedVo> petBreedVos = petMapper.selectPetBreedAppList(petBreedQuery);

        // 按照中文首字母分组，返回只包含 petBreed 的对象
        Map<String, List<PetBreedListVo>> groupedBreeds = petBreedVos.stream()
                .collect(Collectors.groupingBy(pet -> pet.getCnInitials(), // 按照中文首字母分组
                        Collectors.mapping(pet -> new PetBreedListVo(pet.getPetBreed(),pet.getPetBreedEn()), // 将每个品种包装成 PetBreedListVo 对象
                                Collectors.toList())));

        System.out.println(petBreedQuery.getPetClass());
        //获取热门列表
        List<String> hotPetBreeds = petMapper.selectHot(petBreedQuery.getPetClass());
        System.out.println(hotPetBreeds);
        // 构建返回的结果
        Map<String, Object> result = new HashMap<>();
        //petBreed是空的时候才有热门
        if (StringUtils.isEmpty(petBreedQuery.getPetBreed())){
            result.put("hot", hotPetBreeds);
        }
        result.put("breeds", groupedBreeds);

        return result;
    }

    @Override
    public List<PetClassVo> selectPetClass() {

        List<PetClassVo> petClassVos = petMapper.selectPetClass();

        PetClassVo defaultPetClass = new PetClassVo();
        defaultPetClass.setPetClassId("0");
        defaultPetClass.setPetClass("尚未养宠");

        petClassVos.add(0, defaultPetClass);

        return petClassVos;
    }
}
