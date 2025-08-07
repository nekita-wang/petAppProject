package com.petlife.platform.common.sms;

import com.petlife.platform.common.config.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 短信服务管理器
 *
 * @author petlife
 * @date 2025-07-31
 */
@Service
public class SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private List<SmsProvider> smsProviders;

    private Map<String, SmsProvider> providerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        // 异步初始化，避免阻塞启动
        CompletableFuture.runAsync(() -> {
            try {
                for (SmsProvider provider : smsProviders) {
                    providerMap.put(provider.getProviderName(), provider);
                    log.info("注册短信服务提供商: {}", provider.getProviderName());
                }
            } catch (Exception e) {
                log.error("短信服务初始化失败: {}", e.getMessage());
            }
        });
    }

    /**
     * 发送验证码短信
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 发送结果
     */
    public SmsResult sendVerificationCode(String phone, String code) {
        String providerName = smsConfig.getProvider();
        SmsProvider provider = providerMap.get(providerName);

        if (provider == null) {
            log.error("未找到短信服务提供商: {}", providerName);
            return SmsResult.failure("unknown", "未找到短信服务提供商: " + providerName);
        }

        log.info("使用{}服务商发送验证码短信: phone={}", providerName, phone);

        try {
            SmsResult result = provider.sendVerificationCode(phone, code);
            result.setRawResponse(result.getMessage());

            if (result.isSuccess()) {
                log.info("短信发送成功: phone={}, provider={}, message={}",
                        phone, providerName, result.getMessage());
            } else {
                log.warn("短信发送失败: phone={}, provider={}, code={}, message={}",
                        phone, providerName, result.getCode(), result.getMessage());
            }

            return result;
        } catch (Exception e) {
            log.error("短信发送异常: phone={}, provider={}", phone, providerName, e);
            return SmsResult.failure(providerName, "发送异常: " + e.getMessage());
        }
    }

    /**
     * 切换短信服务提供商
     *
     * @param providerName 服务商名称
     * @return 是否切换成功
     */
    public boolean switchProvider(String providerName) {
        if (providerMap.containsKey(providerName)) {
            smsConfig.setProvider(providerName);
            log.info("切换短信服务提供商为: {}", providerName);
            return true;
        } else {
            log.warn("未找到短信服务提供商: {}", providerName);
            return false;
        }
    }

    /**
     * 获取当前使用的服务商
     *
     * @return 当前服务商名称
     */
    public String getCurrentProvider() {
        return smsConfig.getProvider();
    }

    /**
     * 获取所有可用的服务商
     *
     * @return 服务商名称列表
     */
    public String[] getAvailableProviders() {
        return providerMap.keySet().toArray(new String[0]);
    }
}
