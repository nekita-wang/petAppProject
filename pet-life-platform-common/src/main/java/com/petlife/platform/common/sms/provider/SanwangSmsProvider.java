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
 * 三网106短信服务提供商实现
 *
 * @author petlife
 * @date 2025-07-31
 */
@Component("sanwangSmsProvider")
public class SanwangSmsProvider implements SmsProvider {

    private static final Logger log = LoggerFactory.getLogger(SanwangSmsProvider.class);

    @Autowired
    private SmsConfig smsConfig;

    @Override
    public SmsResult sendVerificationCode(String phone, String code) {
        try {
            SmsConfig.SanwangSettings settings = smsConfig.getSanwang();

            // 构建请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE " + settings.getAppCode());

            // 构建查询参数
            Map<String, String> querys = new HashMap<>();
            querys.put("mobile", phone);
            querys.put("param",
                    "**code**:" + code + ",**minute**:" + smsConfig.getTemplates().getLoginCode().getExpireMinutes());
            querys.put("smsSignId", settings.getSmsSignId());
            querys.put("templateId", settings.getTemplateId());

            // 发送请求
            HttpResponse response = SmsHttpUtils.doPost(
                    settings.getHost(),
                    settings.getPath(),
                    headers,
                    querys,
                    null);

            // 处理响应
            String responseContent = SmsHttpUtils.getResponseContent(response);
            log.info("三网106短信发送响应: {}", responseContent);

            // 解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                try {
                    JSONObject jsonResponse = JSON.parseObject(responseContent);

                    // 三网106可能的响应格式：{"code":"0","msg":"成功"} 或 {"code":"200","msg":"成功"}
                    String code_result = jsonResponse.getString("code");
                    String message = jsonResponse.getString("msg");

                    // 成功的状态码可能是 "0", "200", "success" 等
                    if ("0".equals(code_result) || "200".equals(code_result)
                            || "success".equalsIgnoreCase(code_result)) {
                        return SmsResult.success(getProviderName(), "短信发送成功: " + (message != null ? message : ""));
                    } else {
                        String errorMsg = message != null ? message : "短信发送失败，错误码: " + code_result;
                        return SmsResult.failure(getProviderName(), code_result, errorMsg);
                    }
                } catch (Exception e) {
                    log.error("解析三网106短信响应失败", e);
                    return SmsResult.failure(getProviderName(), "响应解析失败: " + e.getMessage());
                }
            } else {
                return SmsResult.failure(getProviderName(),
                        String.valueOf(response.getStatusLine().getStatusCode()),
                        "HTTP请求失败: " + response.getStatusLine().getReasonPhrase());
            }

        } catch (Exception e) {
            log.error("三网106短信发送失败", e);
            return SmsResult.failure(getProviderName(), "发送异常: " + e.getMessage());
        }
    }

    @Override
    public String getProviderName() {
        return "sanwang";
    }
}
