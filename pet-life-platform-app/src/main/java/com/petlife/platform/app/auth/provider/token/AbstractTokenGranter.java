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
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 公共参数校验：手机号、密码非空，手机号格式校验
     */
    protected void publicCheck(LoginDTO loginDTO) {
        if (!StringUtils.hasText(loginDTO.getMobile()) || !StringUtils.hasText(loginDTO.getPassword())) {
            log.warn("手机号或密码为空");
            throw new PetException(AuthExceptionCode.PASSWORD_INCORRECT);
        }
        if (!MOBILE_PATTERN.matcher(loginDTO.getMobile()).matches()) {
            log.warn("手机号格式错误: {}", loginDTO.getMobile());
            throw new PetException(AuthExceptionCode.PHONE_FORMAT_ERROR); // 你可以新增一个手机号格式错误枚举，分开处理
        }
    }

    /**
     * 查询用户并校验账号状态
     */
    protected User checkUser(String mobile) {
        Optional<User> optionalUser = userMapper.selectByPhoneAndStatusIn(mobile, new int[]{0, 2});
        User user = optionalUser.orElseThrow(() -> {
            log.warn("手机号未注册: {}", mobile);
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
