package com.petlife.platform.common.sms;

import com.petlife.platform.common.config.SmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 短信服务配置验证器
 * 在应用启动时验证短信服务配置是否正确
 *
 * @author petlife
 * @date 2025-07-31
 */
@Component
@ConditionalOnProperty(name = "sms.validation.enabled", havingValue = "true", matchIfMissing = false)
public class SmsConfigValidator implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SmsConfigValidator.class);

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private SmsService smsService;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始验证短信服务配置...");

        // 添加超时机制
        CompletableFuture<Void> validationTask = CompletableFuture.runAsync(() -> {
            try {
                validateBasicConfig();
                validateProviderConfig();
                // 跳过服务可用性验证，避免网络超时
                // validateServiceAvailability();
                log.info("短信服务配置验证完成 ✓");
            } catch (Exception e) {
                log.warn("短信服务配置验证失败: {}", e.getMessage());
            }
        });

        try {
            validationTask.get(5, TimeUnit.SECONDS); // 5秒超时
        } catch (TimeoutException e) {
            log.warn("短信服务配置验证超时，跳过验证");
            validationTask.cancel(true);
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
