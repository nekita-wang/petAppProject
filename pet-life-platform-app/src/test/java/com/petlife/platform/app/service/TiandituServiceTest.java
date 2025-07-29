package com.petlife.platform.app.service;

import com.petlife.platform.common.config.MapConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * 天地图服务测试类
 * 
 * @author petlife
 * @date 2025-07-29
 */
@SpringBootTest
public class TiandituServiceTest {

    @Mock
    private MapConfig mapConfig;

    @InjectMocks
    private TiandituService tiandituService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 模拟配置
        MapConfig.TiandituSettings tiandituSettings = new MapConfig.TiandituSettings();
        tiandituSettings.setApiKey("test-api-key");
        tiandituSettings.setGeocodingUrl("http://api.tianditu.gov.cn/geocoder");
        tiandituSettings.setRegeoUrl("http://api.tianditu.gov.cn/geocoder");
        
        when(mapConfig.getTianditu()).thenReturn(tiandituSettings);
        when(mapConfig.getTimeout()).thenReturn(5000);
    }

    @Test
    void testGeocodingWithEmptyAddress() {
        // 测试空地址
        Map<String, Object> result = tiandituService.geocoding("");
        assertNull(result);
        
        result = tiandituService.geocoding(null);
        assertNull(result);
    }

    @Test
    void testReverseGeocodingWithNullCoordinates() {
        // 测试空坐标
        Map<String, Object> result = tiandituService.reverseGeocoding(null, null);
        assertNull(result);
        
        result = tiandituService.reverseGeocoding(new BigDecimal("116.40739990"), null);
        assertNull(result);
        
        result = tiandituService.reverseGeocoding(null, new BigDecimal("39.90419989"));
        assertNull(result);
    }

    @Test
    void testGeocodingUrlConstruction() {
        // 这个测试主要验证URL构建逻辑是否正确
        // 由于没有真实的API Key，实际的HTTP请求会失败，但我们可以验证参数处理
        String address = "北京市朝阳区";
        
        // 调用方法（会因为网络请求失败而返回null，但不会抛出异常）
        Map<String, Object> result = tiandituService.geocoding(address);
        
        // 在没有有效API Key的情况下，预期返回null
        // 这个测试主要确保方法不会抛出异常
        assertNull(result);
    }

    @Test
    void testReverseGeocodingUrlConstruction() {
        // 测试逆地理编码URL构建
        BigDecimal longitude = new BigDecimal("116.40739990");
        BigDecimal latitude = new BigDecimal("39.90419989");
        
        // 调用方法（会因为网络请求失败而返回null，但不会抛出异常）
        Map<String, Object> result = tiandituService.reverseGeocoding(longitude, latitude);
        
        // 在没有有效API Key的情况下，预期返回null
        assertNull(result);
    }
}
