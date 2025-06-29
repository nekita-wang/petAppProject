package com.petlife.platform.app.auth.controller;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.enums.GrantTypeEnum;
import com.petlife.platform.app.auth.provider.CompositeTokenGranterContext;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.app.pojo.dto.LoginDTO;
import com.petlife.platform.app.token.model.AuthUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "登录模块")
public class AuthController {

    @Autowired
    private CompositeTokenGranterContext granterContext;

    /**
     * 登录接口：支持手机号密码登录和手机号验证码登录等
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录接口", notes = "支持手机号密码登录和手机号验证码登录")
    public ResponseData<AuthUserInfo> login(@RequestBody LoginDTO loginDTO) {
        // 从前端传入的 grantType（如 "pwdCaptcha"）找到枚举
        GrantTypeEnum grantType = GrantTypeEnum.getEnumByCode(loginDTO.getGrantType());
        if (Objects.isNull(grantType)) {
            throw new PetException(AuthExceptionCode.GRANTER_INEXISTENCE);
        }

        log.info("用户尝试登录，grantType={}, mobile={}", grantType.getCode(), loginDTO.getMobile());

        // 根据不同策略执行登录
        AuthUserInfo authUserInfo = granterContext.getGranter(grantType).grant(loginDTO);

        return ResponseData.ok(authUserInfo);
    }

    /**
     * 退出登录接口（简单示例，可根据需要结合 Redis 实现 token 删除等）
     */
    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "清理登录状态")
    public ResponseData<String> logout() {
        // 示例：直接返回成功；实际可以结合 redis 删除 token
        log.info("用户退出登录");
        return ResponseData.ok("退出成功");
    }
}
