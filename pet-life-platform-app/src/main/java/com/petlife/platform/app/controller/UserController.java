package com.petlife.platform.app.controller;

import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.pojo.dto.UserProfileDTO;
import com.petlife.platform.app.service.UserService;
import com.petlife.platform.common.core.api.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.petlife.platform.common.utils.DateUtils;

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
    public ResponseData<Void> completeProfile(@Validated @RequestBody UserProfileDTO dto) {
        // 自动转 yyyy-MM-dd
        String normalized = DateUtils.parseDateToYmd(dto.getBirthday());
        if (normalized == null) {
            // 用户有传值，但格式有误
            throw new PetException(400, "生日格式错误，应为 yyyy-MM-dd");
        }
        dto.setBirthday(normalized);
        userService.completeProfile(dto);
        return ResponseData.ok();
    }
}
