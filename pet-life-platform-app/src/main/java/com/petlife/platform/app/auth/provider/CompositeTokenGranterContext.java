package com.petlife.platform.app.auth.provider;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.enums.GrantTypeEnum;
import com.petlife.platform.common.core.exception.PetException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 登录策略分发器，根据 grantType 返回对应登录处理器
 */
@Component
public class CompositeTokenGranterContext {
    private final Map<String, TokenGranterStrategy> granterMap = new ConcurrentHashMap<>();

    /**
     *
     * @description 初始化相关grantType
     * @param granterMap
     * @return
     */
    public CompositeTokenGranterContext(Map<String, TokenGranterStrategy> granterMap) {
        this.granterMap.putAll(granterMap);
    }

    /**
     *
     * @description 根据 grantType 获取对应实例
     * @param grantType
     * @return cn.hfbin.auth.provider.TokenGranter
     */
    public TokenGranterStrategy getGranter(GrantTypeEnum grantType) {
        TokenGranterStrategy tokenGranter = granterMap.get(grantType.getBeanName());
        Optional.ofNullable(tokenGranter).orElseThrow(()->new PetException(AuthExceptionCode.GRANTER_INEXISTENCE));
        return tokenGranter;
    }
}
