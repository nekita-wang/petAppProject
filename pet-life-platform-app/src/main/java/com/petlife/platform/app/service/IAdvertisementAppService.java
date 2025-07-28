package com.petlife.platform.app.service;

import com.petlife.platform.app.dto.AdvertisementAppDto;

/**
 * APP端广告服务接口
 *
 * @author petlife
 * @date 2025-07-27
 */
public interface IAdvertisementAppService {

    /**
     * 根据广告位获取正在运行的广告（APP端专用）
     * 只返回前端必需的字段，并自动增加点击量
     *
     * @param adPosition 广告位标识
     * @return 广告信息DTO，如果没有运行中的广告则返回null
     */
    AdvertisementAppDto getRunningAdvertisement(String adPosition);

    /**
     * 记录广告点击（不返回广告数据，仅统计）
     *
     * @param adPosition 广告位标识
     * @return 是否记录成功
     */
    boolean recordAdClick(String adPosition);
}
