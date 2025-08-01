package com.petlife.platform.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信服务配置类
 *
 * @author petlife
 * @date 2025-07-31
 */
@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {

    /**
     * 当前使用的服务商：wangwei（望为）、sanwang（三网106）
     */
    private String provider = "wangwei";

    /**
     * 请求超时时间（毫秒）
     */
    private Integer timeout = 10000;

    /**
     * 验证码模板配置
     */
    private Templates templates = new Templates();

    /**
     * 望为短信配置
     */
    private WangweiSettings wangwei = new WangweiSettings();

    /**
     * 三网106短信配置
     */
    private SanwangSettings sanwang = new SanwangSettings();

    @Data
    public static class Templates {
        /**
         * 登录验证码模板
         */
        private LoginCodeTemplate loginCode = new LoginCodeTemplate();

        @Data
        public static class LoginCodeTemplate {
            /**
             * 短信内容模板
             */
            private String content = "code:{code}";

            /**
             * 验证码有效期（分钟）
             */
            private Integer expireMinutes = 5;
        }
    }

    @Data
    public static class WangweiSettings {
        /**
         * API主机地址
         */
        private String host = "https://wwsms.market.alicloudapi.com";

        /**
         * API路径
         */
        private String path = "/send_sms";

        /**
         * AppCode
         */
        private String appCode;

        /**
         * AppKey
         */
        private String appKey;

        /**
         * AppSecret
         */
        private String appSecret;

        /**
         * 模板ID
         */
        private String templateId = "wangweisms996";
    }

    @Data
    public static class SanwangSettings {
        /**
         * API主机地址
         */
        private String host = "https://gyytz.market.alicloudapi.com";

        /**
         * API路径
         */
        private String path = "/sms/smsSend";

        /**
         * AppCode
         */
        private String appCode;

        /**
         * AppKey
         */
        private String appKey;

        /**
         * AppSecret
         */
        private String appSecret;

        /**
         * 短信签名ID
         */
        private String smsSignId;

        /**
         * 模板ID
         */
        private String templateId;
    }
}
