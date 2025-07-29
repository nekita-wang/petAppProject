package com.petlife.platform.app.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 地图API测试工具类
 * 
 * @author petlife
 * @date 2025-07-29
 */
public class MapApiTestUtil {

    private static final Logger log = LoggerFactory.getLogger(MapApiTestUtil.class);

    /**
     * 测试天地图API连通性（不需要真实API Key）
     * 
     * @return 测试结果
     */
    public static String testTiandituConnectivity() {
        try {
            // 使用一个无效的API Key进行连通性测试
            String testApiKey = "test-connectivity";
            String address = "北京";
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String postStr = String.format("{\"keyWord\":\"%s\"}", encodedAddress);
            String url = String.format("http://api.tianditu.gov.cn/geocoder?ds=%s&tk=%s",
                    URLEncoder.encode(postStr, StandardCharsets.UTF_8.toString()),
                    testApiKey);

            log.info("测试天地图API连通性: {}", url);

            String response = sendHttpGet(url, 3000);
            
            if (response != null) {
                log.info("天地图API响应: {}", response);
                
                // 检查响应内容
                if (response.contains("\"status\":\"404\"") && response.contains("请求服务权限不足")) {
                    return "✅ 天地图API服务可达，但需要有效的API Key";
                } else if (response.contains("\"status\":\"0\"")) {
                    return "✅ 天地图API服务正常";
                } else {
                    return "⚠️ 天地图API响应异常: " + response;
                }
            } else {
                return "❌ 天地图API服务不可达，请检查网络连接";
            }

        } catch (Exception e) {
            log.error("测试天地图API连通性失败: {}", e.getMessage(), e);
            return "❌ 测试失败: " + e.getMessage();
        }
    }

    /**
     * 测试高德地图API连通性（不需要真实API Key）
     * 
     * @return 测试结果
     */
    public static String testAmapConnectivity() {
        try {
            // 使用一个无效的API Key进行连通性测试
            String testApiKey = "test-connectivity";
            String address = "北京";
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String url = String.format("https://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&output=json",
                    testApiKey, encodedAddress);

            log.info("测试高德地图API连通性: {}", url);

            String response = sendHttpGet(url, 3000);
            
            if (response != null) {
                log.info("高德地图API响应: {}", response);
                
                // 检查响应内容
                if (response.contains("\"status\":\"0\"") && response.contains("INVALID_USER_KEY")) {
                    return "✅ 高德地图API服务可达，但需要有效的API Key";
                } else if (response.contains("\"status\":\"1\"")) {
                    return "✅ 高德地图API服务正常";
                } else {
                    return "⚠️ 高德地图API响应异常: " + response;
                }
            } else {
                return "❌ 高德地图API服务不可达，请检查网络连接";
            }

        } catch (Exception e) {
            log.error("测试高德地图API连通性失败: {}", e.getMessage(), e);
            return "❌ 测试失败: " + e.getMessage();
        }
    }

    /**
     * 发送HTTP GET请求
     */
    private static String sendHttpGet(String url, int timeout) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            HttpGet httpGet = new HttpGet(url);

            // 设置请求超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(timeout)
                    .setSocketTimeout(timeout)
                    .build();
            httpGet.setConfig(requestConfig);

            // 设置请求头
            httpGet.setHeader("User-Agent", "PetLife-Platform/1.0");

            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }

        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }

        return null;
    }

    /**
     * 生成天地图API申请指南
     * 
     * @return 申请指南
     */
    public static String getTiandituApiGuide() {
        return """
                📋 天地图API申请指南：
                
                1. 访问天地图官网：http://lbs.tianditu.gov.cn/
                2. 注册开发者账号（个人或企业）
                3. 登录后进入"控制台"
                4. 选择"应用管理" -> "创建应用"
                5. 填写应用信息：
                   - 应用名称：宠爱生活平台
                   - 应用类型：Web端服务API
                   - 应用描述：宠物生活服务平台的LBS功能
                6. 提交申请，获取API Key
                7. 将API Key配置到application.yml中：
                   map:
                     tianditu:
                       api-key: YOUR_ACTUAL_API_KEY
                
                💰 费用说明：
                - 天地图API完全免费
                - 无需支付任何费用
                - 个人和企业都可以免费使用
                
                🔧 技术支持：
                - 官方文档：http://lbs.tianditu.gov.cn/server/
                - 技术论坛：http://bbs.tianditu.gov.cn/
                """;
    }

    /**
     * 主方法，用于快速测试
     */
    public static void main(String[] args) {
        System.out.println("=== 地图API连通性测试 ===");
        System.out.println();
        
        System.out.println("1. 测试天地图API连通性：");
        System.out.println(testTiandituConnectivity());
        System.out.println();
        
        System.out.println("2. 测试高德地图API连通性：");
        System.out.println(testAmapConnectivity());
        System.out.println();
        
        System.out.println("3. 天地图API申请指南：");
        System.out.println(getTiandituApiGuide());
    }
}
