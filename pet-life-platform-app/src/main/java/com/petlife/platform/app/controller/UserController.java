package com.petlife.platform.app.controller;

import com.petlife.platform.app.pojo.dto.UserProfileDTO;
import com.petlife.platform.app.service.UserService;
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
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 完善个人资料
     * @param dto
     * @return
     */
    @PostMapping("/completeProfile")
    @ApiOperation(value = "完善个人资料", notes = "新用户注册后必须完善个人资料")
    public ResponseData<Void> completeProfile(@RequestBody UserProfileDTO dto) {
        userService.completeProfile(dto);
        return ResponseData.ok();
    }
}
