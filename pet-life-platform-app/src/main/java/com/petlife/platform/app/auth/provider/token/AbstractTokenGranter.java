package com.petlife.platform.app.auth.provider.token;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.provider.TokenGranterStrategy;
import com.petlife.platform.app.mapper.PetMapper;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.enums.UserType;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.entity.User;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.petlife.platform.framework.web.service.TokenService;
import com.petlife.platform.app.mapper.LoginLogMapper;
import com.petlife.platform.common.pojo.entity.LoginLog;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

import java.util.regex.Pattern;

/**
 * 抽象登录令牌生成基类，封装公共校验和Token生成逻辑
 */
@Slf4j
@Component
public abstract class AbstractTokenGranter implements TokenGranterStrategy {
    @Autowired
    public RedisTemplate<String,String> redisTemplate;
    @Autowired
    public UserMapper userMapper;
    @Autowired
    public PetMapper petMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    protected LoginLogMapper loginLogMapper;

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
    public void checkUser(User user) {
        switch (user.getStatus()) {
            case 1:
                log.warn("账号已注销: userId={}", user.getUserId());
                throw new PetException(AuthExceptionCode.ACCOUNT_CANCELLED);
            case 2:
                log.warn("账号已冻结: userId={}", user.getUserId());
                throw new PetException(AuthExceptionCode.ACCOUNT_FROZEN);
//        case 3:
//            log.warn("账号被禁用: userId={}", user.getUserId());
//            throw new PetException(AuthExceptionCode.ACCOUNT_DISABLED);
            default:
                // 正常状态
        }
    }

    /**
     * 生成JWT Token并缓存到Redis
     */
    protected AuthUserInfo createToken(User user) {
        // 创建 LoginUser，给 APP 用户
        LoginUser loginUser = new LoginUser();
        loginUser.setAppUser(user);                  // 设置 APP 用户
        loginUser.setUserId(user.getUserId());
        loginUser.setUserType(UserType.APP_USER);    // 设置用户类型

        // 调用若依生成 token
        String token = tokenService.createToken(loginUser);

        // 返回给前端
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(user.getUserId());
        authUserInfo.setToken(token);
        // expire 建议从配置里读
        authUserInfo.setExpire((long) tokenService.getAppExpireTime() * 60); // expireTime 是分钟，要转秒

        log.info("生成token成功，userId={}, expire={}秒", user.getUserId(), authUserInfo.getExpire());
        return authUserInfo;
    }

    /**
     * 登录日志插入方法，供子类调用
     */
    public void insertLoginLog(LoginLog log) {
        loginLogMapper.insert(log);
    }

    /**
     * 构建登录日志对象，统一封装赋值逻辑
     */
    public LoginLog buildLoginLog(Long userId, int loginMethod, int loginResult, int failCount, String errorReason, LoginDTO loginDTO) {
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            // ignore, fallback to unknown
        }
        String loginIP = null;
        if (request != null) {
            loginIP = request.getHeader("X-Forwarded-For");
            if (loginIP == null || loginIP.isEmpty() || "unknown".equalsIgnoreCase(loginIP)) {
                loginIP = request.getRemoteAddr();
            }
        }
        if (loginIP == null || loginIP.isEmpty() || "unknown".equalsIgnoreCase(loginIP)) {
            loginIP = "unknown";
        }
        String deviceModel = (loginDTO != null && loginDTO.getDeviceModel() != null && !loginDTO.getDeviceModel().isEmpty()) ? loginDTO.getDeviceModel() : "unknown";
        String location = (loginDTO != null && loginDTO.getLocation() != null && !loginDTO.getLocation().isEmpty()) ? loginDTO.getLocation() : "unknown";
        LoginLog log = new LoginLog();
        log.setUserId(userId == null ? 0L : userId);
        log.setLoginMethod(loginMethod);
        log.setLoginTime(new java.util.Date());
        log.setLoginIP(loginIP);
        log.setLocation(location);
        log.setDeviceModel(deviceModel);
        log.setDefaultLocation(null);
        log.setLoginResult(loginResult);
        log.setFailCount(failCount);
        log.setErrorReason(errorReason);
        return log;
    }

    /**
     * 子类实现具体登录流程
     */
    public abstract AuthUserInfo grant(LoginDTO loginDTO);

}
