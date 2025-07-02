package com.petlife.platform.app.controller;

import com.petlife.platform.app.pojo.dto.PetDTO;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.core.api.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "宠物模块")
public class PetController {
    @Autowired
    private PetService petService;


    /**
     * 您养的宠物
     * @param dto
     * @return
     */
    @PostMapping("/addPet")
    @ApiOperation(value = "添加宠物信息", notes = "新用户注册后可选择添加宠物")
    public ResponseData<Void> addPet(@RequestBody PetDTO dto) {
        petService.addPet(dto);
        return ResponseData.ok();
    }

}
