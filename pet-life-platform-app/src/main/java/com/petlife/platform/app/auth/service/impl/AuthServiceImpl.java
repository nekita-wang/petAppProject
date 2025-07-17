package com.petlife.platform.app.auth.service.impl;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.enums.GrantTypeEnum;
import com.petlife.platform.app.auth.provider.CompositeTokenGranterContext;
import com.petlife.platform.app.auth.provider.token.AbstractTokenGranter;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.app.auth.service.AuthService;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.enums.UserType;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.dto.SendCodeDTO;
import com.petlife.platform.common.pojo.entity.User;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.PasswordStrengthUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.common.utils.sign.RsaUtils;
import com.petlife.platform.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import com.petlife.platform.app.mapper.StatusInfoMapper;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private CompositeTokenGranterContext granterContext;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PetService petService;
    @Autowired
    private StatusInfoMapper statusInfoMapper;

    @Override
    public ResponseData<String> sendLoginCode(SendCodeDTO dto) {
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
                default:
            }
        }
        // 60 秒内不能重复发送
        String limitKey = AbstractTokenGranter.VERIFY_CODE_KEY_PREFIX + "limit:" + phone;
        Boolean success = redisTemplate.opsForValue().setIfAbsent(limitKey, "1", Duration.ofSeconds(60));
        if (Boolean.FALSE.equals(success)) {
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
        // TODO: 调用第三方短信服务发送验证码（暂时只打印）
        log.info("发送登录验证码: phone={}, code={}", phone, code);
        // 开发环境（临时返回code）：
        return ResponseData.ok(code);
    }

    @Override
    public ResponseData<AuthUserInfo> login(LoginDTO loginDTO) {
        GrantTypeEnum grantType = GrantTypeEnum.getEnumByCode(loginDTO.getGrantType());
        if (Objects.isNull(grantType)) {
            throw new PetException(AuthExceptionCode.GRANTER_INEXISTENCE);
        }
        log.info("用户尝试登录，grantType={}, phone={}", grantType.getCode(), loginDTO.getPhone());
        AuthUserInfo authUserInfo = granterContext.getGranter(grantType).grant(loginDTO);
        return ResponseData.ok(authUserInfo);
    }

    @Override
    public ResponseData<PasswordStrengthUtils.PasswordStrengthResult> checkPasswordStrength(String password) {
        if (password == null || password.trim().isEmpty()) {
            return ResponseData.error(AuthExceptionCode.PARAMS_MISSING.getCode(), "密码不能为空");
        }
        PasswordStrengthUtils.PasswordStrengthResult result = PasswordStrengthUtils.validatePassword(password);
        return ResponseData.ok(result);
    }

    @Override
    public ResponseData<String> getPublicKey() {
        try {
            String publicKey = RsaUtils.getPublicKey();
            if (publicKey == null || publicKey.isEmpty()) {
                log.warn("RSA公钥为空，请检查RSA密钥对是否正确初始化");
                return ResponseData.error(500, "RSA公钥未初始化，请重启应用");
            }
            log.info("获取RSA公钥成功，长度: {}", publicKey.length());
            return ResponseData.ok(publicKey);
        } catch (Exception e) {
            log.error("获取RSA公钥失败", e);
            return ResponseData.error(500, "获取公钥失败");
        }
    }

    @Override
    public ResponseData<String> logout(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken) || !jwtToken.startsWith("Bearer ")) {
            log.warn("退出登录时未获取到正确的JWT token: {}", jwtToken);
            return ResponseData.error(400, "未获取到正确的JWT token");
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

    /**
     * 校验昵称是否已存在
     */
    public boolean checkNicknameExists(String nickName) {
        if (!StringUtils.hasText(nickName)) {
            return false;
        }
        int count = userMapper.countByNickname(nickName);
        return count > 0;
    }

    private String generateTempToken(String phone) {
        String tempToken = "temp_" + phone + "_" + System.currentTimeMillis();
        redisTemplate.opsForValue().set("temp_token:" + tempToken, phone, Duration.ofMinutes(10));
        return tempToken;
    }

    private AuthUserInfo generateAuthUserInfo(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setAppUser(user);
        loginUser.setUserId(user.getUserId());
        loginUser.setUserType(UserType.APP_USER);
        String token = tokenService.createToken(loginUser);
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(user.getUserId());
        authUserInfo.setToken(token);
        authUserInfo.setExpire((long) tokenService.getAppExpireTime() * 60);
        log.info("生成token成功，userId={}, expire={}秒", user.getUserId(), authUserInfo.getExpire());
        return authUserInfo;
    }
}