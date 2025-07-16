package com.petlife.platform.app.auth.controller;

import com.petlife.platform.app.auth.service.AuthService;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.dto.SendCodeDTO;
import com.petlife.platform.common.pojo.dto.StepRegisterDTO;
import com.petlife.platform.common.pojo.dto.VerifyCodeDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.PasswordStrengthUtils;
import com.petlife.platform.common.utils.ServletUtils;
import com.petlife.platform.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
     * 验证码校验接口（分步注册第一步）
     */
    @PostMapping("/verifyCode")
    @ApiOperation(value = "验证码校验", notes = "分步注册第一步：校验验证码，不创建用户")
    public ResponseData<String> verifyCode(@Validated @RequestBody VerifyCodeDTO dto) {
        return authService.verifyCode(dto);
    }

    /**
     * 分步注册接口（分步注册第二步）
     */
    @PostMapping("/stepRegister")
    @ApiOperation(value = "分步注册", notes = "分步注册第二步：提交个人资料和可选的宠物信息")
    public ResponseData<AuthUserInfo> stepRegister(@Validated @RequestBody StepRegisterDTO dto) {
        return authService.stepRegister(dto);
    }

    /**
     * 密码强度校验接口
     */
    @PostMapping("/checkPasswordStrength")
    @ApiOperation(value = "密码强度校验", notes = "实时校验密码强度")
    public ResponseData<PasswordStrengthUtils.PasswordStrengthResult> checkPasswordStrength(@RequestBody String password) {
        return authService.checkPasswordStrength(password);
    }

    /**
     * 获取RSA公钥接口
     */
    @GetMapping("/getPublicKey")
    @ApiOperation(value = "获取RSA公钥", notes = "用于前端加密密码")
    public ResponseData<String> getPublicKey() {
        return authService.getPublicKey();
    }

    @GetMapping("/checkPhone")
    @ApiOperation(value = "手机号查重", notes = "校验手机号是否已注册")
    public ResponseData<Boolean> checkPhone(@RequestParam String phone) {
        if (!StringUtils.hasText(phone)) {
            return ResponseData.error(400, "手机号不能为空");
        }
        if (!com.petlife.platform.app.auth.provider.token.AbstractTokenGranter.PHONE_PATTERN.matcher(phone).matches()) {
            return ResponseData.error(400, "手机号格式不正确");
        }
        boolean exists = authService.checkPhoneExists(phone);
        if (exists) {
            return ResponseData.error(400, "手机号已注册");
        }
        // 可用时只返回200和data=false
        return ResponseData.ok(false);
    }

    @GetMapping("/checkNickname")
    @ApiOperation(value = "昵称查重", notes = "校验昵称是否已存在")
    public ResponseData<Boolean> checkNickname(@RequestParam String nickName) {
        if (!StringUtils.hasText(nickName)) {
            return ResponseData.error(400, "昵称不能为空");
        }
        if (nickName.length() < 2 || nickName.length() > 10) {
            return ResponseData.error(400, "昵称长度<10字");
        }
        boolean exists = authService.checkNicknameExists(nickName);
        if (exists) {
            return ResponseData.error(400, "昵称已存在");
        }
        // 可用时只返回200和data=false
        return ResponseData.ok(false);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "清理登录状态")
    public ResponseData<String> logout() {
        String jwtToken = ServletUtils.getRequest().getHeader("Authorization");
        return authService.logout(jwtToken);
    }
}
