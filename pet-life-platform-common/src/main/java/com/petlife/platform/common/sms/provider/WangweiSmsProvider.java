package com.petlife.platform.common.sms.provider;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.petlife.platform.common.config.SmsConfig;
import com.petlife.platform.common.sms.SmsHttpUtils;
import com.petlife.platform.common.sms.SmsProvider;
import com.petlife.platform.common.sms.SmsResult;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 望为短信服务提供商实现
 *
 * @author petlife
 * @date 2025-07-31
 */
@Component("wangweiSmsProvider")
public class WangweiSmsProvider implements SmsProvider {

    private static final Logger log = LoggerFactory.getLogger(WangweiSmsProvider.class);

    @Autowired
    private SmsConfig smsConfig;

    @Override
    public SmsResult sendVerificationCode(String phone, String code) {
        try {
            SmsConfig.WangweiSettings settings = smsConfig.getWangwei();

            // 构建请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE " + settings.getAppCode());
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // 构建请求体
            Map<String, String> bodys = new HashMap<>();
            bodys.put("content", smsConfig.getTemplates().getLoginCode().getContent().replace("{code}", code));
            bodys.put("template_id", settings.getTemplateId());
            bodys.put("phone_number", phone);

            // 发送请求
            HttpResponse response = SmsHttpUtils.doPost(
                    settings.getHost(),
                    settings.getPath(),
                    headers,
                    null,
                    bodys);

            // 处理响应
            String responseContent = SmsHttpUtils.getResponseContent(response);
            log.info("望为短信发送响应: {}", responseContent);

            // 解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                try {
                    JSONObject jsonResponse = JSON.parseObject(responseContent);

                    // 望为短信服务实际返回格式：{"status":"OK","request_id":"..."}
                    String status = jsonResponse.getString("status");
                    String requestId = jsonResponse.getString("request_id");

                    if ("OK".equalsIgnoreCase(status)) {
                        return SmsResult.success(getProviderName(), "短信发送成功，请求ID: " + requestId);
                    } else {
                        // 如果有错误信息字段
                        String errorMsg = jsonResponse.getString("message");
                        if (errorMsg == null) {
                            errorMsg = "短信发送失败，状态: " + status;
                        }
                        return SmsResult.failure(getProviderName(), status, errorMsg);
                    }
                } catch (Exception e) {
                    log.error("解析望为短信响应失败", e);
                    return SmsResult.failure(getProviderName(), "响应解析失败: " + e.getMessage());
                }
            } else {
                return SmsResult.failure(getProviderName(),
                        String.valueOf(response.getStatusLine().getStatusCode()),
                        "HTTP请求失败: " + response.getStatusLine().getReasonPhrase());
            }

        } catch (Exception e) {
            log.error("望为短信发送失败", e);
            return SmsResult.failure(getProviderName(), "发送异常: " + e.getMessage());
        }
    }

    @Override
    public String getProviderName() {
        return "wangwei";
    }
}
