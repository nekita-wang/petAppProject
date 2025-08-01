package com.petlife.platform.web.controller.system;

import com.petlife.platform.common.annotation.Log;
import com.petlife.platform.common.core.controller.BaseController;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.enums.BusinessType;
import com.petlife.platform.common.sms.SmsService;
import com.petlife.platform.common.sms.SmsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务管理控制器
 *
 * @author petlife
 * @date 2025-07-31
 */
@RestController
@RequestMapping("/system/sms")
public class SmsManagementController extends BaseController {

    @Autowired
    private SmsService smsService;

    /**
     * 获取短信服务状态
     */
    @PreAuthorize("@ss.hasPermi('system:sms:query')")
    @GetMapping("/status")
    public AjaxResult getStatus() {
        Map<String, Object> data = new HashMap<>();
        data.put("currentProvider", smsService.getCurrentProvider());
        data.put("availableProviders", smsService.getAvailableProviders());
        return AjaxResult.success(data);
    }

    /**
     * 切换短信服务提供商
     */
    @PreAuthorize("@ss.hasPermi('system:sms:edit')")
    @Log(title = "短信服务管理", businessType = BusinessType.UPDATE)
    @PutMapping("/provider/{providerName}")
    public AjaxResult switchProvider(@PathVariable String providerName) {
        boolean success = smsService.switchProvider(providerName);
        if (success) {
            return AjaxResult.success("切换短信服务提供商成功");
        } else {
            return AjaxResult.error("切换失败，未找到指定的服务提供商");
        }
    }

    /**
     * 测试短信发送
     */
    @PreAuthorize("@ss.hasPermi('system:sms:test')")
    @Log(title = "短信服务管理", businessType = BusinessType.OTHER)
    @PostMapping("/test")
    public AjaxResult testSms(@RequestParam String phone) {
        // 生成测试验证码
        String testCode = String.valueOf((int)((Math.random() * 9 + 1) * 100000));
        
        SmsResult result = smsService.sendVerificationCode(phone, testCode);
        
        Map<String, Object> data = new HashMap<>();
        data.put("success", result.isSuccess());
        data.put("provider", result.getProvider());
        data.put("message", result.getMessage());
        data.put("code", result.getCode());
        
        if (result.isSuccess()) {
            return AjaxResult.success("短信发送测试成功", data);
        } else {
            return AjaxResult.error("短信发送测试失败: " + result.getMessage(), data);
        }
    }
}
