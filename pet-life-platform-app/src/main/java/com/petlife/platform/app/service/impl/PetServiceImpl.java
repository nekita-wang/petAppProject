package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.mapper.PetMapper;
import com.petlife.platform.common.pojo.dto.PetBreedQuery;
import com.petlife.platform.common.pojo.dto.PetDTO;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.pojo.dto.PetInfoQuery;
import com.petlife.platform.common.pojo.entity.PetInfo;
import com.petlife.platform.common.pojo.vo.PetBreedListVo;
import com.petlife.platform.common.pojo.vo.PetBreedVo;
import com.petlife.platform.common.pojo.vo.PetClassVo;
import com.petlife.platform.common.utils.DateUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.common.utils.bean.BeanUtils;
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

    @Override
    public void addPet(PetDTO dto) {
        if (!StringUtils.hasText(dto.getNickname()) || dto.getType() == null) {
            throw new PetException(AuthExceptionCode.PARAMS_MISSING);
        }
        if (isPetNicknameExist(dto.getUserId(), dto.getNickname())) {
            throw new PetException(AuthExceptionCode.PET_NICKNAME_EXIST);
        }
        // 保存
        petMapper.insertPet(dto);
    }

    @Override
    public boolean isPetNicknameExist(Long userId, String nickname) {
        return petMapper.countByUserIdAndNickname(userId, nickname) > 0;
    }

    @Override
    public void addPetInfo(PetInfoQuery petInfoQuery) {
        //填写日期不能小于当天日期
        LocalDate now = LocalDate.now();
        if (petInfoQuery.getPetBirthday().isAfter(now)) {
            throw new RuntimeException("宠物生日不能晚于当前时间");
        }
        if (petInfoQuery.getAdoptionDate().isAfter(now)) {
            throw new RuntimeException("到家日期不能晚于当前时间");
        }

        PetInfo info=new PetInfo();
        BeanUtils.copyProperties(info, petInfoQuery);
        info.setUserID(getUserId().toString());
        info.setStatus(0L);

        //判断是否唯一，不唯一抛异常
        if (isPetNicknameExist(Long.valueOf(info.getUserID()), info.getPetNickName())) {
            throw new PetException(AuthExceptionCode.PET_NICKNAME_EXIST);
        }

        petMapper.insertPetInfo(info);
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

        //获取热门列表
        List<String> hotPetBreeds = petMapper.selectHot(petBreedQuery.getPetClass());

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
