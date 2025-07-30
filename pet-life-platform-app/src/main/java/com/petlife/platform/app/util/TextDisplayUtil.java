package com.petlife.platform.app.util;

import java.util.List;

/**
 * 文本显示工具类
 * 处理"附近"功能中的文本显示逻辑
 * 
 * @author petlife
 * @date 2025-07-29
 */
public class TextDisplayUtil {

    /**
     * 截断个性签名显示
     * 根据需求：最多显示2行，超出部分用「…」省略
     * 
     * @param signature 原始个性签名
     * @param maxLength 每行最大字符数（建议20-25个字符）
     * @return 截断后的签名
     */
    public static String truncatePersonalSignature(String signature, int maxLength) {
        if (signature == null || signature.trim().isEmpty()) {
            return "";
        }
        
        String trimmedSignature = signature.trim();
        int maxTotalLength = maxLength * 2; // 两行的总长度
        
        if (trimmedSignature.length() <= maxTotalLength) {
            return trimmedSignature;
        }
        
        // 超出两行，截断并添加省略号
        return trimmedSignature.substring(0, maxTotalLength - 1) + "…";
    }

    /**
     * 格式化宠物品种显示
     * 根据需求：最多显示2个，超过的部分用右上角的"+数字"表示
     * 格式：宠物品种+宠物性别 与 宠物品种+宠物性别 用"|"隔开
     * 
     * @param petBreeds 宠物品种列表（已包含性别信息）
     * @return 格式化后的显示文本
     */
    public static String formatPetBreedsDisplay(List<String> petBreeds) {
        if (petBreeds == null || petBreeds.isEmpty()) {
            return "";
        }
        
        if (petBreeds.size() <= 2) {
            // 不超过2个，全部显示，用"|"分隔
            return String.join("|", petBreeds);
        } else {
            // 超过2个，显示前2个 + 剩余数量
            String displayText = String.join("|", petBreeds.subList(0, 2));
            int remainingCount = petBreeds.size() - 2;
            return displayText + " +" + remainingCount;
        }
    }

    /**
     * 格式化在线时间显示
     * 根据需求文档的具体规则
     * 
     * @param lastLogoffTimeMillis 最后登出时间（毫秒时间戳）
     * @return 在线时间显示文本和颜色信息
     */
    public static OnlineStatusInfo formatOnlineStatus(long lastLogoffTimeMillis) {
        long currentTime = System.currentTimeMillis();
        long diffMillis = currentTime - lastLogoffTimeMillis;
        long diffSeconds = diffMillis / 1000;
        
        if (diffSeconds <= 60) {
            // X<=1分钟，显示"在线"，颜色绿色
            return new OnlineStatusInfo("在线", "green");
        } else if (diffSeconds <= 3600) {
            // 1分钟<X<=1小时，显示具体分钟数，颜色黑色
            long minutes = diffSeconds / 60;
            return new OnlineStatusInfo(minutes + "分前", "black");
        } else if (diffSeconds <= 86400) {
            // 1小时<X<=24小时，显示具体小时数，颜色黑色
            long hours = diffSeconds / 3600;
            return new OnlineStatusInfo(hours + "小时前", "black");
        } else if (diffSeconds <= 2592000) {
            // 24小时<X<=30天，显示具体天数，颜色黑色
            long days = diffSeconds / 86400;
            return new OnlineStatusInfo(days + "天前", "black");
        } else {
            // 30天<X，显示"30天前"，颜色黑色
            return new OnlineStatusInfo("30天前", "black");
        }
    }

    /**
     * 格式化距离显示
     * 
     * @param distanceInMeters 距离（米）
     * @return 格式化的距离字符串
     */
    public static String formatDistance(double distanceInMeters) {
        if (distanceInMeters < 0) {
            return "未知距离";
        }
        
        if (distanceInMeters < 1000) {
            // 小于1000米，显示米
            return Math.round(distanceInMeters) + "m";
        } else if (distanceInMeters < 10000) {
            // 1-10公里，保留1位小数
            return String.format("%.1fkm", distanceInMeters / 1000);
        } else {
            // 大于10公里，显示整数
            return Math.round(distanceInMeters / 1000) + "km";
        }
    }

    /**
     * 验证并清理用户输入的地址
     * 
     * @param address 原始地址
     * @return 清理后的地址
     */
    public static String cleanAddress(String address) {
        if (address == null) {
            return "";
        }
        
        // 去除首尾空格
        String cleaned = address.trim();
        
        // 去除多余的空格
        cleaned = cleaned.replaceAll("\\s+", " ");
        
        // 限制最大长度（避免过长的地址）
        if (cleaned.length() > 100) {
            cleaned = cleaned.substring(0, 100);
        }
        
        return cleaned;
    }

    /**
     * 在线状态信息内部类
     */
    public static class OnlineStatusInfo {
        private final String text;
        private final String color;
        
        public OnlineStatusInfo(String text, String color) {
            this.text = text;
            this.color = color;
        }
        
        public String getText() {
            return text;
        }
        
        public String getColor() {
            return color;
        }
        
        public boolean isOnline() {
            return "green".equals(color);
        }
    }

    /**
     * 格式化用户昵称显示（防止过长）
     * 
     * @param nickName 原始昵称
     * @param maxLength 最大长度
     * @return 格式化后的昵称
     */
    public static String formatNickName(String nickName, int maxLength) {
        if (nickName == null || nickName.trim().isEmpty()) {
            return "未设置昵称";
        }
        
        String trimmed = nickName.trim();
        if (trimmed.length() <= maxLength) {
            return trimmed;
        }
        
        return trimmed.substring(0, maxLength - 1) + "…";
    }

    /**
     * 检查字符串是否包含敏感词（简单实现）
     * 
     * @param text 待检查的文本
     * @return 是否包含敏感词
     */
    public static boolean containsSensitiveWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        // 这里可以集成更复杂的敏感词过滤逻辑
        String[] sensitiveWords = {"广告", "推广", "微信", "QQ", "电话"};
        
        String lowerText = text.toLowerCase();
        for (String word : sensitiveWords) {
            if (lowerText.contains(word.toLowerCase())) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * 过滤敏感词
     * 
     * @param text 原始文本
     * @return 过滤后的文本
     */
    public static String filterSensitiveWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }
        
        String[] sensitiveWords = {"广告", "推广", "微信", "QQ"};
        String filtered = text;
        
        for (String word : sensitiveWords) {
            filtered = filtered.replaceAll("(?i)" + word, "***");
        }
        
        return filtered;
    }
}
