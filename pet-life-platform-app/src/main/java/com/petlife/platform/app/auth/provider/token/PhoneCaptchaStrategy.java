package com.petlife.platform.app.auth.provider.token;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.pojo.dto.LoginDTO;
import com.petlife.platform.app.pojo.entity.User;
import com.petlife.platform.app.token.model.AuthUserInfo;
import com.petlife.platform.common.core.exception.PetException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            throw new PetException(AuthExceptionCode.CODE_IS_EMPTY);
        }

        String redisKey = VERIFY_CODE_KEY_PREFIX + phone;
        String cachedCode = redisTemplate.opsForValue().get(redisKey);
        if (cachedCode == null) {
            log.warn("验证码已过期或未发送: phone={}", phone);
            throw new PetException(AuthExceptionCode.CODE_EXPIRED);
        }
        if (!code.equals(cachedCode)) {
            log.warn("验证码不正确: 输入={}, 实际={}", code, cachedCode);
            throw new PetException(AuthExceptionCode.CODE_INCORRECT);
        }

        // 删除验证码
        redisTemplate.delete(redisKey);

        // 3️⃣ 查询用户是否存在
        Optional<User> optionalUser = userMapper.selectByPhoneAndStatusIn(phone, new int[]{0, 2});
        User user;
        boolean isNewUser = false;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            // 校验账号状态
            checkUser(user);
        } else {
            // 新用户：注册
            user = new User();
            user.setPhone(phone);
            user.setStatus((byte) 0); // 正常状态
            user.setNickName("用户" + phone.substring(phone.length() - 4));

            // 设置默认密码：用 BCrypt 加密固定值或随机值
            String defaultPassword = passwordEncoder.encode("123456");
            user.setPassword(defaultPassword);

            // 设置必填字段的默认值
            user.setBirthday(LocalDate.parse("2000-01-01")); // 默认生日
            user.setAvatarUrl("http://example.com/avatar.jpg"); // 默认头像
            user.setMinor((byte) 0); // 默认成年

            userMapper.insert(user);
            log.info("新用户注册成功: phone={}", phone);
            isNewUser = true;
        }

        // 4️⃣ 生成 token 返回
        AuthUserInfo authUserInfo = createToken(user);
        authUserInfo.setNewUser(isNewUser);

        log.info("手机号验证码登录成功: userId={}, isNewUser={}", user.getUserId(), isNewUser);
        return authUserInfo;
    }
}
