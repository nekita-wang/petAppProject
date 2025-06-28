package com.petlife.platform.app.token.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性类，对应 yml 中 pet.jwt 节点
 */
@Data
@Component
@ConfigurationProperties(prefix = "pet.jwt")
public class JwtProperties {

    /**
     * Token 过期时间（秒），默认 12 小时
     */
    private Long expire = 43200L;

    /**
     * Refresh Token 过期时间（秒），默认 24 小时
     */
    private Long refreshExpire = 86400L;

    /**
     * 时间误差允许范围（秒）
     */
    private Long allowableErrorTime = 60L;

}