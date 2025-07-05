package com.petlife.platform.framework.web.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import com.petlife.platform.common.enums.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.petlife.platform.common.constant.CacheConstants;
import com.petlife.platform.common.constant.Constants;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.core.redis.RedisCache;
import com.petlife.platform.common.utils.ServletUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.common.utils.ip.AddressUtils;
import com.petlife.platform.common.utils.ip.IpUtils;
import com.petlife.platform.common.utils.uuid.IdUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token验证处理
 * 
 * @author pet-life
 */
@Component
public class TokenService
{
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 分别配置后台与App的token过期时间（单位：分钟）
    @Value("${token.admin.expireTime}")
    private int adminExpireTime;

    @Value("${token.app.expireTime}")
    private int appExpireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TWENTY = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    public int getAppExpireTime() {
        return appExpireTime;
    }

    public int getAdminExpireTime() {
        return adminExpireTime;
    }

    /**
     * 根据请求获取 LoginUser
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userType = (String) claims.get(Constants.JWT_USER_TYPE);
                String redisKey = buildTokenKey(uuid, userType);
                return redisCache.getCacheObject(redisKey);
            } catch (Exception e) {
                log.error("获取用户信息异常: {}", e.getMessage());
            }
        }
        return null;
    }


    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除 token
     */
    public void delLoginUser(String token) {
        if (StringUtils.isEmpty(token)) {
            log.warn("退出登录时未传入 token，不做处理");
            return;
        }

        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userType = (String) claims.get(Constants.JWT_USER_TYPE);
                if (StringUtils.isEmpty(userType)) {
                    log.warn("退出登录时 userType 为空，不删除 redis key");
                    return;
                }
                redisCache.deleteObject(buildTokenKey(uuid, userType));
            } catch (Exception e) {
                log.error("退出登录时解析 token 出错: {}", e.getMessage());
            }
        }
    }

    /**
     * 创建令牌
     * 
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser)
    {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        claims.put(Constants.JWT_USERNAME, loginUser.getUsername());
        claims.put(Constants.JWT_USER_TYPE, loginUser.getUserType().name()); // SYS_USER / APP_USER
        return createToken(claims);
    }

    /**
     * 验证 token 快过期时自动刷新
     */
    public void verifyToken(LoginUser loginUser) {
        if (loginUser.getExpireTime() - System.currentTimeMillis() <= MILLIS_MINUTE_TWENTY) {
            refreshToken(loginUser);
        }
    }


    /**
     * 刷新令牌有效期
     * 
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        long expire;

        if (loginUser.getUserType() == null) {
            throw new RuntimeException("用户类型不能为空");
        }

        String userTypeStr = loginUser.getUserType().name();

       if (loginUser.getUserType() == UserType.SYS_USER) {
            expire = adminExpireTime * MILLIS_MINUTE;
            loginUser.setExpireTime(loginUser.getLoginTime() + expire);
            redisCache.setCacheObject(buildTokenKey(loginUser.getToken(), Constants.USER_TYPE_ADMIN), loginUser, adminExpireTime, TimeUnit.MINUTES);
        } else if (loginUser.getUserType() == UserType.APP_USER) {
            expire = appExpireTime * MILLIS_MINUTE;
            loginUser.setExpireTime(loginUser.getLoginTime() + expire);
            redisCache.setCacheObject(buildTokenKey(loginUser.getToken(), Constants.USER_TYPE_APP), loginUser, appExpireTime, TimeUnit.MINUTES);
        }
        // else if (...) 可继续扩展其他端
    }

    /**
     * 设置用户代理信息
     * 
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser)
    {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr();

        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 拼接 Redis Key
     */
    private String buildTokenKey(String uuid, String userType) {
        return CacheConstants.LOGIN_TOKEN_KEY + userType.toLowerCase() + ":" + uuid;
    }

    /**
     * 从请求头取出 token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token 不能为空");
        }
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }
}
