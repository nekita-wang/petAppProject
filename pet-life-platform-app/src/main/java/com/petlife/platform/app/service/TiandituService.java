package com.petlife.platform.app.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.petlife.platform.common.config.MapConfig;
import com.petlife.platform.common.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 天地图API服务
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Service
public class TiandituService {

    private static final Logger log = LoggerFactory.getLogger(TiandituService.class);

    @Autowired
    private MapConfig mapConfig;

    /**
     * 地理编码：将地址转换为坐标
     * 
     * @param address 地址
     * @return 坐标信息
     */
    public Map<String, Object> geocoding(String address) {
        if (StringUtils.isEmpty(address)) {
            return null;
        }

        try {
            // 构建请求参数
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String postStr = String.format("{\"keyWord\":\"%s\"}", encodedAddress);
            String url = String.format("%s?ds=%s&tk=%s",
                    mapConfig.getTianditu().getGeocodingUrl(),
                    URLEncoder.encode(postStr, StandardCharsets.UTF_8.toString()),
                    mapConfig.getTianditu().getApiKey());

            log.info("天地图地理编码请求: {}", url);

            // 发送HTTP请求
            String response = sendHttpGet(url);
            if (StringUtils.isEmpty(response)) {
                log.error("天地图API响应为空");
                return null;
            }

            log.info("天地图API响应: {}", response);

            // 解析响应
            return parseGeocodingResponse(response, address);

        } catch (Exception e) {
            log.error("调用天地图地理编码API失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 逆地理编码：将坐标转换为地址
     * 
     * @param longitude 经度
     * @param latitude  纬度
     * @return 地址信息
     */
    public Map<String, Object> reverseGeocoding(BigDecimal longitude, BigDecimal latitude) {
        if (longitude == null || latitude == null) {
            return null;
        }

        try {
            // 构建请求参数
            String postStr = String.format("{\"lon\":%s,\"lat\":%s,\"ver\":1}", longitude, latitude);
            String url = String.format("%s?postStr=%s&type=geocode&tk=%s",
                    mapConfig.getTianditu().getRegeoUrl(),
                    URLEncoder.encode(postStr, StandardCharsets.UTF_8.toString()),
                    mapConfig.getTianditu().getApiKey());

            log.info("天地图逆地理编码请求: {}", url);

            // 发送HTTP请求
            String response = sendHttpGet(url);
            if (StringUtils.isEmpty(response)) {
                log.error("天地图API响应为空");
                return null;
            }

            log.info("天地图API响应: {}", response);

            // 解析响应
            return parseReverseGeocodingResponse(response, longitude, latitude);

        } catch (Exception e) {
            log.error("调用天地图逆地理编码API失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 发送HTTP GET请求
     */
    private String sendHttpGet(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            HttpGet httpGet = new HttpGet(url);

            // 设置请求超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(mapConfig.getTimeout())
                    .setSocketTimeout(mapConfig.getTimeout())
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
     * 解析地理编码响应
     */
    private Map<String, Object> parseGeocodingResponse(String response, String originalAddress) {
        try {
            JSONObject jsonResponse = JSON.parseObject(response);
            String status = jsonResponse.getString("status");

            if (!"0".equals(status)) {
                log.error("天地图API返回错误状态: {}, msg: {}", status, jsonResponse.getString("msg"));
                return null;
            }

            JSONObject location = jsonResponse.getJSONObject("location");
            if (location == null) {
                log.warn("未找到地址对应的坐标: {}", originalAddress);
                return null;
            }

            Map<String, Object> result = new HashMap<>();
            result.put("address", originalAddress);
            result.put("longitude", location.getString("lon"));
            result.put("latitude", location.getString("lat"));
            result.put("level", location.getString("level"));

            return result;

        } catch (Exception e) {
            log.error("解析天地图地理编码响应失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 解析逆地理编码响应
     */
    private Map<String, Object> parseReverseGeocodingResponse(String response, BigDecimal longitude, BigDecimal latitude) {
        try {
            JSONObject jsonResponse = JSON.parseObject(response);
            String status = jsonResponse.getString("status");

            if (!"0".equals(status)) {
                log.error("天地图API返回错误状态: {}, msg: {}", status, jsonResponse.getString("msg"));
                return null;
            }

            JSONObject result = jsonResponse.getJSONObject("result");
            if (result == null) {
                log.warn("未找到坐标对应的地址: {},{}", longitude, latitude);
                return null;
            }

            String formattedAddress = result.getString("formatted_address");
            JSONObject addressComponent = result.getJSONObject("addressComponent");

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("longitude", longitude.toString());
            resultMap.put("latitude", latitude.toString());
            resultMap.put("address", formattedAddress);

            if (addressComponent != null) {
                resultMap.put("city", addressComponent.getString("city"));
                resultMap.put("address_detail", addressComponent.getString("address"));
                resultMap.put("poi", addressComponent.getString("poi"));
                resultMap.put("road", addressComponent.getString("road"));
            }

            return resultMap;

        } catch (Exception e) {
            log.error("解析天地图逆地理编码响应失败: {}", e.getMessage(), e);
            return null;
        }
    }
}
