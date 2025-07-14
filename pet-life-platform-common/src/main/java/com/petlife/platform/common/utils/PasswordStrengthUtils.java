package com.petlife.platform.common.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 密码强度校验工具类
 */
public class PasswordStrengthUtils {

    /**
     * 密码强度校验结果
     */
    @Data
    public static class PasswordStrengthResult {
        private boolean valid;
        private List<String> errors;
        private int strength; // 0-4，0最弱，4最强

        public PasswordStrengthResult() {
            this.errors = new ArrayList<>();
        }

        public void addError(String error) {
            this.errors.add(error);
        }
    }

    /**
     * 校验密码强度
     * 要求：10位包含大小写字母、数字、特殊符号
     * 
     * @param password 明文密码
     * @return 校验结果
     */
    public static PasswordStrengthResult validatePassword(String password) {
        PasswordStrengthResult result = new PasswordStrengthResult();
        
        if (password == null || password.trim().isEmpty()) {
            result.addError("密码不能为空");
            return result;
        }

        // 1. 长度校验
        if (password.length() < 10) {
            result.addError("密码长度至少10位");
        }

        // 2. 大写字母校验
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            result.addError("缺少大写字母");
        }

        // 3. 小写字母校验
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            result.addError("缺少小写字母");
        }

        // 4. 数字校验
        if (!Pattern.compile("\\d").matcher(password).find()) {
            result.addError("缺少数字");
        }

        // 5. 特殊符号校验
        if (!Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]").matcher(password).find()) {
            result.addError("特殊字符至少1个");
        }

        // 6. 计算强度
        int strength = 0;
        if (password.length() >= 10) strength++;
        if (Pattern.compile("[A-Z]").matcher(password).find()) strength++;
        if (Pattern.compile("[a-z]").matcher(password).find()) strength++;
        if (Pattern.compile("\\d").matcher(password).find()) strength++;
        if (Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]").matcher(password).find()) strength++;
        
        result.setStrength(strength);
        result.setValid(result.getErrors().isEmpty());
        
        return result;
    }

    /**
     * 获取密码强度描述
     * 
     * @param strength 强度值(0-4)
     * @return 强度描述
     */
    public static String getStrengthDescription(int strength) {
        switch (strength) {
            case 0:
            case 1:
                return "很弱";
            case 2:
                return "弱";
            case 3:
                return "中等";
            case 4:
                return "强";
            case 5:
                return "很强";
            default:
                return "未知";
        }
    }

    /**
     * 获取密码强度颜色
     * 
     * @param strength 强度值(0-4)
     * @return 颜色值
     */
    public static String getStrengthColor(int strength) {
        switch (strength) {
            case 0:
            case 1:
                return "#ff4d4f"; // 红色
            case 2:
                return "#faad14"; // 橙色
            case 3:
                return "#1890ff"; // 蓝色
            case 4:
            case 5:
                return "#52c41a"; // 绿色
            default:
                return "#d9d9d9"; // 灰色
        }
    }
} 