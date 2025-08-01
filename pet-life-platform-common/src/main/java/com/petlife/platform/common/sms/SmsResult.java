package com.petlife.platform.common.sms;

import lombok.Data;

/**
 * 短信发送结果
 *
 * @author petlife
 * @date 2025-07-31
 */
@Data
public class SmsResult {

    /**
     * 是否发送成功
     */
    private boolean success;

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 原始响应数据
     */
    private String rawResponse;

    /**
     * 服务商名称
     */
    private String provider;

    /**
     * 创建成功结果
     *
     * @param provider 服务商名称
     * @param message 消息
     * @return 成功结果
     */
    public static SmsResult success(String provider, String message) {
        SmsResult result = new SmsResult();
        result.setSuccess(true);
        result.setProvider(provider);
        result.setMessage(message);
        result.setCode("200");
        return result;
    }

    /**
     * 创建失败结果
     *
     * @param provider 服务商名称
     * @param code 错误码
     * @param message 错误消息
     * @return 失败结果
     */
    public static SmsResult failure(String provider, String code, String message) {
        SmsResult result = new SmsResult();
        result.setSuccess(false);
        result.setProvider(provider);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 创建失败结果
     *
     * @param provider 服务商名称
     * @param message 错误消息
     * @return 失败结果
     */
    public static SmsResult failure(String provider, String message) {
        return failure(provider, "500", message);
    }
}
