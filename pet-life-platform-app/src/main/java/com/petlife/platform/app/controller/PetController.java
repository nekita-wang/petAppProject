package com.petlife.platform.app.controller;

import com.petlife.platform.common.core.domain.R;
import com.petlife.platform.common.pojo.dto.PetBreedQuery;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.dto.PetInfoDTO;
import com.petlife.platform.common.pojo.vo.PetClassVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/app/pet")
@Api(tags = "宠物模块")
public class PetController {
    @Autowired
    private PetService petService;


    /**
     * 您养的宠物
     * @param petInfoDTO
     * @return
     */
    @PostMapping("/addPet")
    @ApiOperation(value = "添加宠物信息", notes = "新用户注册后可选择添加宠物")
    public ResponseData<Void> addPet(@Validated @RequestBody PetInfoDTO petInfoDTO) {
        petService.addPetInfo(petInfoDTO);
        return ResponseData.ok();
    }

    /**
     * 跳过宠物信息
     * @return
     */
    @PostMapping("/skipPet")
    @ApiOperation(value = "跳过宠物信息", notes = "新用户注册后可以选择跳过宠物信息填写")
    public ResponseData<Void> skipPet() {
        // 这里可以记录用户跳过宠物信息的操作，或者直接返回成功
        // 由于用户已经注册成功，这里只是确认跳过操作
        return ResponseData.ok();
    }


    /**
     * 宠物查询
     * @param petBreedQuery
     * @return
     */
    @GetMapping("/breeds")
    public R list( PetBreedQuery petBreedQuery)
    {
        Map<String, Object> map = petService.selectPetBreedAppList(petBreedQuery);
        return R.ok(map);
    }


    /**
     * 查询宠物类别
     */
    @GetMapping("/pet")
    public R petList()
    {
        List<PetClassVo> petClassVos = petService.selectPetClass();
        return R.ok(petClassVos);
    }

}
