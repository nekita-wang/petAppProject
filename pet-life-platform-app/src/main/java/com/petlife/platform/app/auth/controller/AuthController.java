package com.petlife.platform.app.auth.controller;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.enums.GrantTypeEnum;
import com.petlife.platform.app.auth.provider.CompositeTokenGranterContext;
import com.petlife.platform.app.auth.provider.token.AbstractTokenGranter;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.common.pojo.dto.SendCodeDTO;
import com.petlife.platform.common.pojo.entity.User;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.ServletUtils;
import com.petlife.platform.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.petlife.platform.framework.web.service.TokenService;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/app/auth")
@Api(tags = "登录模块")
public class AuthController {

    @Autowired
    private CompositeTokenGranterContext granterContext;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    /**
     * 发送登录验证码
     */
    @PostMapping("/sendCode")
    @ApiOperation(value = "发送验证码", notes = "手机号格式合法则发送验证码")
    public ResponseData<String> sendLoginCode(@RequestBody(required = false) SendCodeDTO dto) {
        String phone = dto.getPhone();
        if (!StringUtils.hasText(phone)) {
            log.warn("手机号不能为空");
            return ResponseData.error(AuthExceptionCode.PHONE_IS_EMPTY);
        }

        if (!AbstractTokenGranter.PHONE_PATTERN.matcher(phone).matches()) {
            log.warn("手机号格式不合法: {}", phone);
            return ResponseData.error(AuthExceptionCode.PHONE_FORMAT_ERROR);
        }

        // 如果手机号已注册，检查状态
        User user = userMapper.selectByPhone(phone);
        if (user != null) {
            switch (user.getStatus()) {
                case 1:
                    log.warn("账号已注销: userId={}", user.getUserId());
                    throw new PetException(AuthExceptionCode.ACCOUNT_CANCELLED);
                case 2:
                    log.warn("账号已冻结: userId={}", user.getUserId());
                    throw new PetException(AuthExceptionCode.ACCOUNT_FROZEN);
//                case 3:
//                    log.warn("账号被禁用: userId={}", user.getUserId());
//                    throw new PetException(AuthExceptionCode.ACCOUNT_DISABLED);
                default:
                    // 正常
            }
        }

        // 60 秒内不能重复发送
        String limitKey = AbstractTokenGranter.VERIFY_CODE_KEY_PREFIX + "limit:" + phone;
        Boolean exists = redisTemplate.hasKey(limitKey);
        if (Boolean.TRUE.equals(exists)) {
            log.warn("发送验证码过于频繁: {}", phone);
            return ResponseData.error(AuthExceptionCode.CODE_SEND_TOO_FREQUENT);
        }

        // 生成 6 位验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        // 保存验证码，有效期 5 分钟
        redisTemplate.opsForValue().set(
                AbstractTokenGranter.VERIFY_CODE_KEY_PREFIX + phone,
                code,
                Duration.ofMinutes(5)
        );

        // 保存发送频率标识，有效期 60 秒
        redisTemplate.opsForValue().set(
                limitKey,
                "1",
                Duration.ofSeconds(60)
        );

        // TODO: 调用第三方短信服务发送验证码（暂时只打印）
        log.info("发送登录验证码: phone={}, code={}", phone, code);

        // 开发环境（临时返回code）：
        return ResponseData.ok(code);
    }

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

        log.info("用户尝试登录，grantType={}, phone={}", grantType.getCode(), loginDTO.getPhone());

        // 根据不同策略执行登录
        AuthUserInfo authUserInfo = granterContext.getGranter(grantType).grant(loginDTO);

        return ResponseData.ok(authUserInfo);
    }


    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "清理登录状态")
    public ResponseData<String> logout() {
        String jwtToken = ServletUtils.getRequest().getHeader("Authorization");

        if (StringUtils.isEmpty(jwtToken) || !jwtToken.startsWith("Bearer ")) {
            log.warn("退出登录时未获取到正确的JWT token: {}", jwtToken);
            return ResponseData.error(400,"未获取到正确的JWT token");
        }

        jwtToken = jwtToken.replace("Bearer ", "");

        try {
            boolean deleted = tokenService.delLoginUser(jwtToken);
            if (deleted) {
                log.info("退出登录成功，已删除 redis 中的 token");
                return ResponseData.ok("退出成功");
            } else {
                log.warn("退出登录时未找到 redis 中的 token");
                return ResponseData.ok("退出成功（但未找到有效会话）");
            }
        } catch (Exception e) {
            log.error("退出登录时解析 token 出错: {}", e.getMessage());
            return ResponseData.error(500, "退出登录失败，请稍后再试");
        }
    }
}
