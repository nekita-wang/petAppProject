package com.petlife.platform.app.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
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
 * 高德地图API服务
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Service
public class AmapService {

    private static final Logger log = LoggerFactory.getLogger(AmapService.class);

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
            // 构建请求URL
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String url = String.format("%s?key=%s&address=%s&output=json",
                    mapConfig.getAmap().getGeocodingUrl(),
                    mapConfig.getAmap().getApiKey(),
                    encodedAddress);

            log.info("高德地图地理编码请求: {}", url);

            // 发送HTTP请求
            String response = sendHttpGet(url);
            if (StringUtils.isEmpty(response)) {
                log.error("高德地图API响应为空");
                return null;
            }

            log.info("高德地图API响应: {}", response);

            // 解析响应
            return parseGeocodingResponse(response, address);

        } catch (Exception e) {
            log.error("调用高德地图地理编码API失败: {}", e.getMessage(), e);
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
            // 构建请求URL
            String location = longitude + "," + latitude;
            String url = String.format("%s?key=%s&location=%s&output=json&radius=1000&extensions=all",
                    mapConfig.getAmap().getRegeoUrl(),
                    mapConfig.getAmap().getApiKey(),
                    location);

            log.info("高德地图逆地理编码请求: {}", url);

            // 发送HTTP请求
            String response = sendHttpGet(url);
            if (StringUtils.isEmpty(response)) {
                log.error("高德地图API响应为空");
                return null;
            }

            log.info("高德地图API响应: {}", response);

            // 解析响应
            return parseReverseGeocodingResponse(response, longitude, latitude);

        } catch (Exception e) {
            log.error("调用高德地图逆地理编码API失败: {}", e.getMessage(), e);
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

            if (!"1".equals(status)) {
                log.error("高德地图API返回错误状态: {}, info: {}", status, jsonResponse.getString("info"));
                return null;
            }

            JSONArray geocodes = jsonResponse.getJSONArray("geocodes");
            if (geocodes == null || geocodes.isEmpty()) {
                log.warn("未找到地址对应的坐标: {}", originalAddress);
                return null;
            }

            JSONObject geocode = geocodes.getJSONObject(0);
            String location = geocode.getString("location");
            if (StringUtils.isEmpty(location)) {
                return null;
            }

            String[] coords = location.split(",");
            if (coords.length != 2) {
                return null;
            }

            Map<String, Object> result = new HashMap<>();
            result.put("address", originalAddress);
            result.put("longitude", coords[0]);
            result.put("latitude", coords[1]);
            result.put("province", geocode.getString("province"));
            result.put("city", geocode.getString("city"));
            result.put("district", geocode.getString("district"));
            result.put("township", geocode.getString("township"));
            result.put("level", geocode.getString("level"));

            return result;

        } catch (Exception e) {
            log.error("解析高德地图地理编码响应失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 解析逆地理编码响应
     */
    private Map<String, Object> parseReverseGeocodingResponse(String response, BigDecimal longitude,
            BigDecimal latitude) {
        try {
            JSONObject jsonResponse = JSON.parseObject(response);
            String status = jsonResponse.getString("status");

            if (!"1".equals(status)) {
                log.error("高德地图API返回错误状态: {}, info: {}", status, jsonResponse.getString("info"));
                return null;
            }

            JSONObject regeocode = jsonResponse.getJSONObject("regeocode");
            if (regeocode == null) {
                return null;
            }

            JSONObject addressComponent = regeocode.getJSONObject("addressComponent");
            String formattedAddress = regeocode.getString("formatted_address");

            Map<String, Object> result = new HashMap<>();
            result.put("longitude", longitude.toString());
            result.put("latitude", latitude.toString());
            result.put("address", formattedAddress);

            if (addressComponent != null) {
                result.put("province", addressComponent.getString("province"));
                result.put("city", addressComponent.getString("city"));
                result.put("district", addressComponent.getString("district"));
                result.put("township", addressComponent.getString("township"));
                result.put("adcode", addressComponent.getString("adcode"));
            }

            return result;

        } catch (Exception e) {
            log.error("解析高德地图逆地理编码响应失败: {}", e.getMessage(), e);
            return null;
        }
    }
}
