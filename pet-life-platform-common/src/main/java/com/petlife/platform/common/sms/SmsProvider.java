package com.petlife.platform.common.sms;

/**
 * 短信服务提供商接口
 *
 * @author petlife
 * @date 2025-07-31
 */
public interface SmsProvider {

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     * @param code 验证码
     * @return 发送结果
     */
    SmsResult sendVerificationCode(String phone, String code);

    /**
     * 获取服务商名称
     *
     * @return 服务商名称
     */
    String getProviderName();
}
