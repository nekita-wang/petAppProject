package com.petlife.platform.web.task;

import com.petlife.platform.common.config.RuoYiConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 定时清理临时图片
 */
@Component
public class TempAvatarCleaner {

    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点
    public void cleanTempAvatars() {
        File tempDir = new File(RuoYiConfig.getTempAvatarPath());
        File[] files = tempDir.listFiles();
        if (files != null) {
            for (File file : files) {
                // 24小时未用就删除
                if (System.currentTimeMillis() - file.lastModified() > 24 * 60 * 60 * 1000) {
                    file.delete();
                }
            }
        }
    }
}