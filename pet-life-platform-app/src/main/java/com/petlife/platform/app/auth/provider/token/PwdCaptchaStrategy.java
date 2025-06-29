package com.petlife.platform.app.auth.provider.token;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.app.pojo.dto.LoginDTO;
import com.petlife.platform.app.pojo.entity.User;
import com.petlife.platform.app.token.model.AuthUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("pwdCaptchaStrategy")
public class PwdCaptchaStrategy extends AbstractTokenGranter {

    /**
     * 实现手机号+密码登录流程
     */
    @Override
    public AuthUserInfo grant(LoginDTO loginDTO) {
        // 1. 校验手机号格式和非空
        publicCheck(loginDTO);

        String mobile = loginDTO.getMobile();
        String password = loginDTO.getPassword();

        // 2. 查找用户并检查状态
        User user = checkUser(mobile);

        Long userId = user.getUserId().longValue();
        String errorCountKey = "login:pwd:errors:" + userId;

        // 3. 获取当前密码错误次数
        String errorCountStr = redisTemplate.opsForValue().get(errorCountKey);
        int errorCount = Optional.ofNullable(errorCountStr).map(Integer::parseInt).orElse(0);

        // 4. 如果错误次数 >= 5，锁定
        if (errorCount >= 5) {
            log.warn("用户ID={} 密码错误次数过多，账号已锁定30分钟", userId);
            throw new PetException(AuthExceptionCode.ACCOUNT_LOCKED);
        }

        // 5. 校验密码
        boolean isPasswordCorrect = BCrypt.checkpw(password, user.getPassword());
        if (!isPasswordCorrect) {
            errorCount++;
            redisTemplate.opsForValue().set(errorCountKey, String.valueOf(errorCount), 30, TimeUnit.MINUTES);

            if (errorCount >= 5) {
                log.warn("用户ID={} 连续密码错误{}次，账号锁定30分钟", userId, errorCount);
                throw new PetException(AuthExceptionCode.ACCOUNT_LOCKED);
            } else {
                log.warn("用户ID={} 密码错误，当前错误次数={}", userId, errorCount);
                throw new PetException(AuthExceptionCode.PASSWORD_INCORRECT);
            }
        }

        // 6. 密码正确：清除错误次数
        redisTemplate.delete(errorCountKey);

        // 7. 登录成功生成 token
        AuthUserInfo authUserInfo = createToken(user);

        log.info("用户ID={} 登录成功", userId);
        return authUserInfo;
    }
}
