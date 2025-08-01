package com.petlife.platform.common.config;

import com.petlife.platform.common.sms.SmsService;
import com.petlife.platform.common.sms.provider.SanwangSmsProvider;
import com.petlife.platform.common.sms.provider.WangweiSmsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信服务自动配置类
 *
 * @author petlife
 * @date 2025-07-31
 */
@Configuration
public class SmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WangweiSmsProvider wangweiSmsProvider() {
        return new WangweiSmsProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public SanwangSmsProvider sanwangSmsProvider() {
        return new SanwangSmsProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public SmsService smsService() {
        return new SmsService();
    }
}
