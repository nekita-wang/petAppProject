package com.petlife.platform.common.sms;

import com.petlife.platform.common.config.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 短信服务配置验证器
 * 在应用启动时验证短信服务配置是否正确
 *
 * @author petlife
 * @date 2025-07-31
 */
 @Component
public class SmsConfigValidator implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SmsConfigValidator.class);

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private SmsService smsService;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始验证短信服务配置...");

        try {
            // 验证基本配置
            validateBasicConfig();

            // 验证服务提供商配置
            validateProviderConfig();

            // 验证服务可用性
            validateServiceAvailability();

            log.info("短信服务配置验证完成 ✓");

        } catch (Exception e) {
            log.error("短信服务配置验证失败: {}", e.getMessage());
            log.warn("请检查短信服务配置，某些功能可能无法正常使用");
        }
    }

    private void validateBasicConfig() {
        if (smsConfig.getProvider() == null || smsConfig.getProvider().trim().isEmpty()) {
            throw new IllegalArgumentException("短信服务提供商未配置");
        }

        if (smsConfig.getTimeout() == null || smsConfig.getTimeout() <= 0) {
            throw new IllegalArgumentException("短信服务超时时间配置无效");
        }

        log.info("基本配置验证通过: provider={}, timeout={}ms",
                smsConfig.getProvider(), smsConfig.getTimeout());
    }

    private void validateProviderConfig() {
        String provider = smsConfig.getProvider();

        switch (provider) {
            case "wangwei":
                validateWangweiConfig();
                break;
            case "sanwang":
                validateSanwangConfig();
                break;
            default:
                throw new IllegalArgumentException("不支持的短信服务提供商: " + provider);
        }

        log.info("服务提供商配置验证通过: {}", provider);
    }

    private void validateWangweiConfig() {
        SmsConfig.WangweiSettings settings = smsConfig.getWangwei();

        if (settings.getHost() == null || settings.getHost().trim().isEmpty()) {
            throw new IllegalArgumentException("望为短信服务主机地址未配置");
        }

        if (settings.getAppCode() == null || settings.getAppCode().trim().isEmpty()) {
            throw new IllegalArgumentException("望为短信服务AppCode未配置");
        }

        if (settings.getTemplateId() == null || settings.getTemplateId().trim().isEmpty()) {
            throw new IllegalArgumentException("望为短信服务模板ID未配置");
        }
    }

    private void validateSanwangConfig() {
        SmsConfig.SanwangSettings settings = smsConfig.getSanwang();

        if (settings.getHost() == null || settings.getHost().trim().isEmpty()) {
            throw new IllegalArgumentException("三网106短信服务主机地址未配置");
        }

        if (settings.getAppCode() == null || settings.getAppCode().trim().isEmpty()) {
            throw new IllegalArgumentException("三网106短信服务AppCode未配置");
        }

        if (settings.getSmsSignId() == null || settings.getSmsSignId().trim().isEmpty()) {
            throw new IllegalArgumentException("三网106短信服务签名ID未配置");
        }

        if (settings.getTemplateId() == null || settings.getTemplateId().trim().isEmpty()) {
            throw new IllegalArgumentException("三网106短信服务模板ID未配置");
        }
    }

    private void validateServiceAvailability() {
        String currentProvider = smsService.getCurrentProvider();
        String[] availableProviders = smsService.getAvailableProviders();

        if (availableProviders == null || availableProviders.length == 0) {
            throw new IllegalStateException("没有可用的短信服务提供商");
        }

        boolean providerFound = false;
        for (String provider : availableProviders) {
            if (provider.equals(currentProvider)) {
                providerFound = true;
                break;
            }
        }

        if (!providerFound) {
            throw new IllegalStateException("当前配置的短信服务提供商不可用: " + currentProvider);
        }

        log.info("短信服务可用性验证通过: 当前提供商={}, 可用提供商={}",
                currentProvider, String.join(", ", availableProviders));
    }
}
