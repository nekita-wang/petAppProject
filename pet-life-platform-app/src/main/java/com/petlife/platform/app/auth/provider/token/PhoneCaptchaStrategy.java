package com.petlife.platform.app.auth.provider.token;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.pojo.dto.LoginDTO;
import com.petlife.platform.app.pojo.entity.User;
import com.petlife.platform.app.token.model.AuthUserInfo;
import com.petlife.platform.common.core.exception.PetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("phoneCaptchaStrategy")
public class PhoneCaptchaStrategy extends AbstractTokenGranter {

    @Override
    public AuthUserInfo grant(LoginDTO loginDTO) {
        // 1️⃣ 校验手机号格式（去除空格、横线 + 格式合法）
        publicCheck(loginDTO);
        String phone = loginDTO.getPhone();

        // 2️⃣ 校验验证码非空
        String code = loginDTO.getCode();
        if (!org.springframework.util.StringUtils.hasText(code)) {
            log.warn("验证码为空");
            throw new PetException(AuthExceptionCode.PASSWORD_IS_EMPTY); // 建议新增 CODE_IS_EMPTY 错误码
        }

        // 3️⃣ 校验验证码是否正确
        String redisKey = VERIFY_CODE_KEY_PREFIX + phone;
        String cachedCode = redisTemplate.opsForValue().get(redisKey);
        if (cachedCode == null) {
            log.warn("验证码已过期或未发送: phone={}", phone);
            throw new PetException(AuthExceptionCode.LOGIN_TOO_FREQUENT); // 建议新增 CODE_EXPIRED 错误码
        }
        if (!code.equals(cachedCode)) {
            log.warn("验证码不正确: 输入={}, 实际={}", code, cachedCode);
            throw new PetException(AuthExceptionCode.PASSWORD_INCORRECT); // 建议新增 CODE_INCORRECT 错误码
        }
        // 删除验证码，防止重复使用
        redisTemplate.delete(redisKey);

        // 4️⃣ 查询用户是否存在
        Optional<User> optionalUser = userMapper.selectByPhoneAndStatusIn(phone, new int[]{0, 2});
        User user;
        boolean isNewUser = false;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            // 校验用户状态：冻结、注销等
            checkUser(phone);
        } else {
            // 5️⃣ 新用户：注册
            user = new User();
            user.setPhone(phone);
            user.setStatus((byte) 0); // 正常
            userMapper.insert(user);
            log.info("新用户注册成功: phone={}", phone);
            isNewUser = true;
        }

        // 6️⃣ 生成 token
        AuthUserInfo authUserInfo = createToken(user);
        authUserInfo.setNewUser(isNewUser);

        log.info("手机号验证码登录成功: userId={}, isNewUser={}", user.getUserId(), isNewUser);
        return authUserInfo;
    }
}
