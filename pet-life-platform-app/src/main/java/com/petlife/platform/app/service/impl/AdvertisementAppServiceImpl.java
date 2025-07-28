package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.dto.AdvertisementAppDto;
import com.petlife.platform.app.mapper.AdvertisementAppMapper;
import com.petlife.platform.app.service.IAdvertisementAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * APP端广告服务实现
 *
 * @author petlife
 * @date 2025-07-27
 */
@Service
public class AdvertisementAppServiceImpl implements IAdvertisementAppService {

    private static final Logger log = LoggerFactory.getLogger(AdvertisementAppServiceImpl.class);

    @Autowired
    private AdvertisementAppMapper advertisementAppMapper;

    /**
     * 根据广告位获取正在运行的广告（APP端专用）
     */
    @Override
    public AdvertisementAppDto getRunningAdvertisement(String adPosition) {
        try {
            // 查询正在运行的广告（直接返回DTO，无需转换）
            AdvertisementAppDto advertisement = advertisementAppMapper.selectRunningAdvertisementByPosition(adPosition);

            if (advertisement == null) {
                log.debug("广告位 {} 暂无运行中的广告", adPosition);
                return null;
            }

            // 自动增加点击量（展示即算一次点击）
            try {
                advertisementAppMapper.updateClickCountByPosition(adPosition);
                log.debug("广告位 {} 点击量已增加", adPosition);
                // 更新返回数据中的点击量
                advertisement.setClickCount(advertisement.getClickCount() + 1);
            } catch (Exception e) {
                log.warn("更新广告点击量失败: {}", e.getMessage());
                // 不影响广告数据返回
            }

            return advertisement;

        } catch (Exception e) {
            log.error("获取广告位 {} 的广告失败: {}", adPosition, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 记录广告点击（不返回广告数据，仅统计）
     */
    @Override
    public boolean recordAdClick(String adPosition) {
        try {
            // 检查是否有运行中的广告
            Integer adId = advertisementAppMapper.checkRunningAdvertisementExists(adPosition);

            if (adId == null) {
                log.debug("广告位 {} 暂无运行中的广告，无法记录点击", adPosition);
                return false;
            }

            // 增加点击量
            int result = advertisementAppMapper.updateClickCountByPosition(adPosition);

            if (result > 0) {
                log.info("广告位 {} 点击量记录成功", adPosition);
                return true;
            } else {
                log.warn("广告位 {} 点击量记录失败", adPosition);
                return false;
            }

        } catch (Exception e) {
            log.error("记录广告位 {} 点击失败: {}", adPosition, e.getMessage(), e);
            return false;
        }
    }
}
