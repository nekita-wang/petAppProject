package com.petlife.platform.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 地图API配置（支持高德地图和天地图）
 *
 * @author petlife
 * @date 2025-07-29
 */
@Data
@Component
@ConfigurationProperties(prefix = "map")
public class MapConfig {

    /**
     * 地图服务提供商类型：amap（高德）、tianditu（天地图）
     */
    private String provider = "tianditu";

    /**
     * 高德地图配置
     */
    private AmapSettings amap = new AmapSettings();

    /**
     * 天地图配置
     */
    private TiandituSettings tianditu = new TiandituSettings();

    /**
     * 请求超时时间（毫秒）
     */
    private Integer timeout = 5000;

    @Data
    public static class AmapSettings {
        /**
         * 高德地图API Key
         */
        private String apiKey;

        /**
         * 地理编码API地址
         */
        private String geocodingUrl = "https://restapi.amap.com/v3/geocode/geo";

        /**
         * 逆地理编码API地址
         */
        private String regeoUrl = "https://restapi.amap.com/v3/geocode/regeo";
    }

    @Data
    public static class TiandituSettings {
        /**
         * 天地图API Key
         */
        private String apiKey;

        /**
         * 地理编码API地址
         */
        private String geocodingUrl = "http://api.tianditu.gov.cn/geocoder";

        /**
         * 逆地理编码API地址
         */
        private String regeoUrl = "http://api.tianditu.gov.cn/geocoder";
    }
}
