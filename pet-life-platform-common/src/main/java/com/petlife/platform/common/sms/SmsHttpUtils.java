package com.petlife.platform.common.sms;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 短信服务HTTP工具类
 *
 * @author petlife
 * @date 2025-07-31
 */
public class SmsHttpUtils {

    private static final Logger log = LoggerFactory.getLogger(SmsHttpUtils.class);

    /**
     * 发送POST请求
     *
     * @param host 主机地址
     * @param path 路径
     * @param headers 请求头
     * @param querys 查询参数
     * @param bodys 请求体参数
     * @return HTTP响应
     * @throws Exception 异常
     */
    public static HttpResponse doPost(String host, String path, Map<String, String> headers,
                                      Map<String, String> querys, Map<String, String> bodys) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        try {
            // 构建URL
            StringBuilder urlBuilder = new StringBuilder(host).append(path);
            if (querys != null && !querys.isEmpty()) {
                urlBuilder.append("?");
                boolean first = true;
                for (Map.Entry<String, String> entry : querys.entrySet()) {
                    if (!first) {
                        urlBuilder.append("&");
                    }
                    urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                    first = false;
                }
            }

            HttpPost httpPost = new HttpPost(urlBuilder.toString());

            // 设置请求头
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }

            // 设置请求体
            if (bodys != null && !bodys.isEmpty()) {
                List<NameValuePair> params = new ArrayList<>();
                for (Map.Entry<String, String> entry : bodys.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            }

            log.info("发送短信请求: URL={}, Headers={}, Body={}", urlBuilder.toString(), headers, bodys);
            
            return httpClient.execute(httpPost);
            
        } catch (Exception e) {
            log.error("发送HTTP请求失败", e);
            throw e;
        }
    }

    /**
     * 获取响应内容
     *
     * @param response HTTP响应
     * @return 响应内容
     * @throws IOException IO异常
     */
    public static String getResponseContent(HttpResponse response) throws IOException {
        if (response.getEntity() != null) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }
}
