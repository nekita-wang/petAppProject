package com.petlife.platform.app.service;

import com.petlife.platform.common.config.MapConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 统一地图服务（支持多种地图提供商）
 * 
 * @author petlife
 * @date 2025-07-29
 */
@Service
public class MapService {

    private static final Logger log = LoggerFactory.getLogger(MapService.class);

    @Autowired
    private MapConfig mapConfig;

    @Autowired
    private AmapService amapService;

    @Autowired
    private TiandituService tiandituService;

    /**
     * 地理编码：将地址转换为坐标
     * 
     * @param address 地址
     * @return 坐标信息
     */
    public Map<String, Object> geocoding(String address) {
        String provider = mapConfig.getProvider();
        
        log.info("使用地图服务提供商: {}, 进行地理编码: {}", provider, address);
        
        switch (provider.toLowerCase()) {
            case "amap":
                return amapService.geocoding(address);
            case "tianditu":
                return tiandituService.geocoding(address);
            default:
                log.warn("未知的地图服务提供商: {}, 使用默认天地图服务", provider);
                return tiandituService.geocoding(address);
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
        String provider = mapConfig.getProvider();
        
        log.info("使用地图服务提供商: {}, 进行逆地理编码: {},{}", provider, longitude, latitude);
        
        switch (provider.toLowerCase()) {
            case "amap":
                return amapService.reverseGeocoding(longitude, latitude);
            case "tianditu":
                return tiandituService.reverseGeocoding(longitude, latitude);
            default:
                log.warn("未知的地图服务提供商: {}, 使用默认天地图服务", provider);
                return tiandituService.reverseGeocoding(longitude, latitude);
        }
    }

    /**
     * 获取当前使用的地图服务提供商
     * 
     * @return 提供商名称
     */
    public String getCurrentProvider() {
        return mapConfig.getProvider();
    }

    /**
     * 检查当前地图服务是否可用
     * 
     * @return 是否可用
     */
    public boolean isServiceAvailable() {
        String provider = mapConfig.getProvider();
        
        switch (provider.toLowerCase()) {
            case "amap":
                return mapConfig.getAmap().getApiKey() != null && !mapConfig.getAmap().getApiKey().trim().isEmpty();
            case "tianditu":
                return mapConfig.getTianditu().getApiKey() != null && !mapConfig.getTianditu().getApiKey().trim().isEmpty();
            default:
                return false;
        }
    }
}
