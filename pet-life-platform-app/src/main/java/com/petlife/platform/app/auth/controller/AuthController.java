package com.petlife.platform.app.auth.controller;

import com.petlife.platform.app.auth.service.AuthService;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.dto.SendCodeDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/app/auth")
@Api(tags = "登录模块")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 发送登录验证码
     */
    @PostMapping("/sendCode")
    @ApiOperation(value = "发送验证码", notes = "手机号格式合法则发送验证码")
    public ResponseData<String> sendLoginCode(@RequestBody(required = false) SendCodeDTO dto) {
        return authService.sendLoginCode(dto);
    }

    /**
     * 登录接口：支持手机号密码登录和手机号验证码登录等
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录接口", notes = "支持手机号密码登录和手机号验证码登录")
    public ResponseData<AuthUserInfo> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    /**
     * 获取RSA公钥接口
     */
    @GetMapping("/getPublicKey")
    @ApiOperation(value = "获取RSA公钥", notes = "用于前端加密密码")
    public ResponseData<String> getPublicKey() {
        return authService.getPublicKey();
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "清理登录状态")
    public ResponseData<String> logout() {
        String jwtToken = ServletUtils.getRequest().getHeader("Authorization");
        return authService.logout(jwtToken);
    }
}
