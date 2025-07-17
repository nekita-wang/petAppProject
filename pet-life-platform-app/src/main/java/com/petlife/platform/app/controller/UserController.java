package com.petlife.platform.app.controller;


import com.petlife.platform.app.service.UserService;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.dto.UserProfileRegisterDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/app/user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerProfile")
    @ApiOperation(value = "完善个人资料", notes = "分步注册第二步：提交个人资料")
    public ResponseData<AuthUserInfo> registerProfile(@Validated @RequestBody UserProfileRegisterDTO dto) {
        return userService.registerProfile(dto);
    }
}
