package com.petlife.platform.app.auth.provider.token;


import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.provider.TokenGranterStrategy;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.app.pojo.dto.LoginDTO;
import com.petlife.platform.app.pojo.entity.User;
import com.petlife.platform.app.token.JwtUtil;
import com.petlife.platform.app.token.config.JwtProperties;
import com.petlife.platform.app.token.model.AuthUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 抽象登录令牌生成基类，封装公共校验和Token生成逻辑
 */
@Slf4j
@Component
public abstract class AbstractTokenGranter implements TokenGranterStrategy {

    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected StringRedisTemplate redisTemplate;
    @Autowired
    protected JwtProperties jwtProperties;

    /**
     * 手机号正则，支持中国大陆手机号
     */
    public static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    // Redis 验证码 key 前缀
    public static final String VERIFY_CODE_KEY_PREFIX = "login:code:";

    /**
     * 公共参数校验：手机号、手机号格式校验
     */
    protected void publicCheck(LoginDTO loginDTO) {
        String rawPhone = loginDTO.getPhone();

        if (rawPhone == null) {
            log.warn("手机号字段缺失");
            //同样返回手机号不能为空
            throw new PetException(AuthExceptionCode.PHONE_IS_EMPTY);
        }

        // 去除空格、横线
        String cleanedPhone = rawPhone.replaceAll("[\\s\\-]", "");

        // 将清洗后的手机号重新写回 DTO
        loginDTO.setPhone(cleanedPhone);

        if (!StringUtils.hasText(cleanedPhone)) {
            log.warn("手机号为空");
            throw new PetException(AuthExceptionCode.PHONE_IS_EMPTY);
        }

        if (!PHONE_PATTERN.matcher(cleanedPhone).matches()) {
            log.warn("手机号格式错误: {}", loginDTO.getPhone());
            throw new PetException(AuthExceptionCode.PHONE_FORMAT_ERROR);
        }
    }

    /**
     * 查询用户并校验账号状态
     */
    protected User checkUser(String phone) {
        Optional<User> optionalUser = userMapper.selectByPhoneAndStatusIn(phone, new int[]{0, 2});
        User user = optionalUser.orElseThrow(() -> {
            log.warn("手机号未注册: {}", phone);
            return new PetException(AuthExceptionCode.ACCOUNT_NOT_EXIST);
        });

        switch (user.getStatus()) {
            case 1:
                log.warn("账号已注销: userId={}", user.getUserId());
                throw new PetException(AuthExceptionCode.ACCOUNT_CANCELLED);
            case 2:
                log.warn("账号已冻结: userId={}", user.getUserId());
                throw new PetException(AuthExceptionCode.ACCOUNT_FROZEN);
//            case 3:
//                log.warn("账号被禁用: userId={}", user.getUserId());
//                throw new PetException(AuthExceptionCode.ACCOUNT_DISABLED);
            default:
                // 正常状态，继续执行
        }
        return user;
    }

    /**
     * 生成JWT Token并缓存到Redis
     */
    protected AuthUserInfo createToken(User user) {
        Long expireSeconds = jwtProperties.getExpire() != null ? jwtProperties.getExpire() : 43200L;

        String token = JwtUtil.generateToken(user.getUserId().toString(), expireSeconds);

        String redisKey = "login:token:" + user.getUserId();
        redisTemplate.opsForValue().set(redisKey, token, Duration.ofSeconds(expireSeconds));

        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(user.getUserId());
        authUserInfo.setToken(token);
        authUserInfo.setExpire(expireSeconds);

        log.info("生成token成功，userId={}, expire={}秒", user.getUserId(), expireSeconds);
        return authUserInfo;
    }

    /**
     * 子类实现具体登录流程
     */
    public abstract AuthUserInfo grant(LoginDTO loginDTO);

}
