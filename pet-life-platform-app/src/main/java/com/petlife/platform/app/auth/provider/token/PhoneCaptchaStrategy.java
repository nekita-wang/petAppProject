package com.petlife.platform.app.auth.provider.token;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.entity.User;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.core.exception.PetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service("phoneCaptchaStrategy")
public class PhoneCaptchaStrategy extends AbstractTokenGranter {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public AuthUserInfo grant(LoginDTO loginDTO) {
        // 1️⃣ 校验手机号格式（去除空格、横线 + 格式合法）
        publicCheck(loginDTO);
        String phone = loginDTO.getPhone();

        // 2️⃣  校验验证码
        String code = loginDTO.getCode();
        if (!org.springframework.util.StringUtils.hasText(code)) {
            log.warn("验证码为空");
            // 登录日志-失败
            insertLoginLog(buildLoginLog(0L, 0, 1, 1, "验证码为空", loginDTO));
            throw new PetException(AuthExceptionCode.CODE_IS_EMPTY);
        }

        String redisKey = VERIFY_CODE_KEY_PREFIX + phone;
        String cachedCode = redisTemplate.opsForValue().get(redisKey);
        if (cachedCode == null) {
            log.warn("验证码已过期或未发送: phone={}", phone);
            // 登录日志-失败
            insertLoginLog(buildLoginLog(0L, 0, 1, 1, "验证码过期", loginDTO));
            throw new PetException(AuthExceptionCode.CODE_EXPIRED);
        }
        if (!code.equals(cachedCode)) {
            log.warn("验证码不正确: 输入={}, 实际={}", code, cachedCode);
            // 登录日志-失败
            insertLoginLog(buildLoginLog(0L, 0, 1, 1, "验证码错误", loginDTO));
            throw new PetException(AuthExceptionCode.CODE_INCORRECT);
        }

        // 删除验证码
        redisTemplate.delete(redisKey);

        // 3️⃣ 查询用户是否存在
        Optional<User> optionalUser = userMapper.selectByPhoneAndStatusIn(phone, new int[]{0, 2});

        if (optionalUser.isPresent()) {
            // 用户存在：直接登录
            User user = optionalUser.get();
            // 校验账号状态
            checkUser(user);
            // 生成 token 返回
            AuthUserInfo authUserInfo = createToken(user);
            // 设置 needPetInfo 字段，只有真正有宠物信息才为 false
            boolean hasPet = petMapper.countByUserId(user.getUserId()) > 0;
            authUserInfo.setNeedPetInfo(!hasPet);
            // 登录日志-成功
            insertLoginLog(buildLoginLog(user.getUserId(), 0, 0, 0, null, loginDTO));
            log.info("手机号验证码登录成功: userId={},", user.getUserId());
            return authUserInfo;
        } else {
            // 用户不存在：抛出特定异常，引导用户注册
            log.info("手机号未注册，需要引导用户完成注册: phone={}", phone);
            // 登录日志-失败
            insertLoginLog(buildLoginLog(0L, 0, 1, 1, "手机号未注册", loginDTO));
            throw new PetException(AuthExceptionCode.ACCOUNT_NOT_EXIST.getCode(), "该手机号未注册，请先完成注册");
        }
    }


}
