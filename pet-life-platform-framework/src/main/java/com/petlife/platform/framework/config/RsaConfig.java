package com.petlife.platform.framework.config;

import com.petlife.platform.common.utils.sign.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * RSA配置类，确保RSA密钥对在应用启动时正确初始化
 */
@Component
public class RsaConfig implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(RsaConfig.class);
    
    @Override
    public void run(String... args) throws Exception {
        try {
            // 检查RSA密钥对是否已初始化
            String publicKey = RsaUtils.getPublicKey();
            String privateKey = RsaUtils.getPrivateKey();
            
            if (publicKey == null || publicKey.isEmpty() || privateKey == null || privateKey.isEmpty()) {
                log.warn("RSA密钥对未初始化，正在生成...");
                // 手动生成密钥对
                generateRsaKeyPair();
                log.info("RSA密钥对生成成功");
            } else {
                log.info("RSA密钥对已存在，公钥长度: {}, 私钥长度: {}", 
                    publicKey.length(), privateKey.length());
            }
        } catch (Exception e) {
            log.error("RSA密钥对初始化失败", e);
            throw e;
        }
    }
    
    /**
     * 生成RSA密钥对
     */
    private void generateRsaKeyPair() throws Exception {
        java.security.KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        java.security.KeyPair keyPair = keyPairGenerator.generateKeyPair();
        java.security.interfaces.RSAPublicKey rsaPublicKey = (java.security.interfaces.RSAPublicKey) keyPair.getPublic();
        java.security.interfaces.RSAPrivateKey rsaPrivateKey = (java.security.interfaces.RSAPrivateKey) keyPair.getPrivate();
        
        String publicKeyString = org.apache.commons.codec.binary.Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = org.apache.commons.codec.binary.Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        
        // 使用反射设置RSA工具类的静态字段
        java.lang.reflect.Field publicKeyField = RsaUtils.class.getDeclaredField("publicKeyStr");
        java.lang.reflect.Field privateKeyField = RsaUtils.class.getDeclaredField("privateKeyStr");
        java.lang.reflect.Field rsaKeyPairField = RsaUtils.class.getDeclaredField("rsaKeyPair");
        
        publicKeyField.setAccessible(true);
        privateKeyField.setAccessible(true);
        rsaKeyPairField.setAccessible(true);
        
        publicKeyField.set(null, publicKeyString);
        privateKeyField.set(null, privateKeyString);
        
        RsaUtils.RsaKeyPair rsaKeyPair = (RsaUtils.RsaKeyPair) rsaKeyPairField.get(null);
        rsaKeyPair.setPublicKey(publicKeyString);
        rsaKeyPair.setPrivateKey(privateKeyString);
    }
} 