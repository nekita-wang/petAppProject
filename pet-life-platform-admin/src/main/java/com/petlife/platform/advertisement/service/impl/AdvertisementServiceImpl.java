package com.petlife.platform.advertisement.service.impl;

import java.util.List;
import com.petlife.platform.common.utils.DateUtils;
import com.petlife.platform.common.utils.SecurityUtils;
import com.petlife.platform.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petlife.platform.advertisement.mapper.AdvertisementMapper;
import com.petlife.platform.advertisement.domain.Advertisement;
import com.petlife.platform.advertisement.domain.AdvertisementStatistics;
import com.petlife.platform.advertisement.service.IAdvertisementService;

/**
 * 广告信息Service业务层处理
 * 
 * @author petlife
 * @date 2025-01-02
 */
@Service
public class AdvertisementServiceImpl implements IAdvertisementService {
    @Autowired
    private AdvertisementMapper advertisementMapper;

    /**
     * 查询广告信息
     * 
     * @param id 广告信息主键
     * @return 广告信息
     */
    @Override
    public Advertisement selectAdvertisementById(Integer id) {
        return advertisementMapper.selectAdvertisementById(id);
    }

    /**
     * 查询广告信息列表
     * 
     * @param advertisement 广告信息
     * @return 广告信息
     */
    @Override
    public List<Advertisement> selectAdvertisementList(Advertisement advertisement) {
        return advertisementMapper.selectAdvertisementList(advertisement);
    }

    /**
     * 新增广告信息
     * 
     * @param advertisement 广告信息
     * @return 结果
     */
    @Override
    public int insertAdvertisement(Advertisement advertisement) {
        // 设置默认值
        advertisement.setClickCount(0);
        advertisement.setCleard(0);
        advertisement.setStatus(1); // 默认运行中
        advertisement.setCreateTime(DateUtils.getNowDate());
        advertisement.setLastUpdateTime(DateUtils.getNowDate());
        advertisement.setCreator(SecurityUtils.getUsername());
        advertisement.setLastUpdater(SecurityUtils.getUsername());

        return advertisementMapper.insertAdvertisement(advertisement);
    }

    /**
     * 修改广告信息
     * 
     * @param advertisement 广告信息
     * @return 结果
     */
    @Override
    public int updateAdvertisement(Advertisement advertisement) {
        advertisement.setLastUpdateTime(DateUtils.getNowDate());
        advertisement.setLastUpdater(SecurityUtils.getUsername());
        return advertisementMapper.updateAdvertisement(advertisement);
    }

    /**
     * 批量删除广告信息
     * 
     * @param ids 需要删除的广告信息主键
     * @return 结果
     */
    @Override
    public int deleteAdvertisementByIds(Integer[] ids) {
        return advertisementMapper.deleteAdvertisementByIds(ids);
    }

    /**
     * 删除广告信息信息
     * 
     * @param id 广告信息主键
     * @return 结果
     */
    @Override
    public int deleteAdvertisementById(Integer id) {
        return advertisementMapper.deleteAdvertisementById(id);
    }

    /**
     * 根据广告位查询正在运行的广告
     * 
     * @param adPosition 广告位标识
     * @return 广告信息
     */
    @Override
    public Advertisement selectRunningAdvertisementByPosition(String adPosition) {
        return advertisementMapper.selectRunningAdvertisementByPosition(adPosition);
    }

    /**
     * 更新广告点击量
     * 
     * @param id 广告ID
     * @return 结果
     */
    @Override
    public int updateClickCount(Integer id) {
        return advertisementMapper.updateClickCount(id);
    }

    /**
     * 更新广告结清状态
     * 
     * @param id          广告ID
     * @param cleard      结清状态
     * @param lastUpdater 更新人
     * @return 结果
     */
    @Override
    public int updateCleardStatus(Integer id, Integer cleard, String lastUpdater) {
        return advertisementMapper.updateCleardStatus(id, cleard, lastUpdater);
    }

    /**
     * 校验广告位是否已存在运行中的广告
     *
     * @param advertisement 广告信息
     * @return 结果
     */
    @Override
    public String checkAdPositionUnique(Advertisement advertisement) {
        Integer adId = StringUtils.isNull(advertisement.getId()) ? -1 : advertisement.getId();
        Advertisement info = advertisementMapper.selectRunningAdvertisementByPosition(advertisement.getAdPosition());
        if (StringUtils.isNotNull(info) && info.getId().intValue() != adId.intValue()) {
            return "该广告位已存在运行中的广告";
        }
        return "0";
    }

    /**
     * 失效广告
     *
     * @param id 广告ID
     * @return 结果
     */
    @Override
    public int invalidateAdvertisement(Integer id) {
        try {
            return advertisementMapper.invalidateAdvertisement(id, SecurityUtils.getUsername());
        } catch (Exception e) {
            // 如果是唯一约束冲突，尝试先删除相同广告位的已失效广告
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("uk_ad_position")) {
                Advertisement advertisement = advertisementMapper.selectAdvertisementById(id);
                if (advertisement != null) {
                    // 删除相同广告位的已失效广告
                    List<Advertisement> existingInvalidAds = advertisementMapper.selectAdvertisementList(
                            new Advertisement() {
                                {
                                    setAdPosition(advertisement.getAdPosition());
                                    setStatus(3); // 已失效状态
                                }
                            });

                    for (Advertisement existingAd : existingInvalidAds) {
                        if (!existingAd.getId().equals(id)) {
                            advertisementMapper.deleteAdvertisementById(existingAd.getId());
                        }
                    }

                    // 重新尝试失效操作
                    return advertisementMapper.invalidateAdvertisement(id, SecurityUtils.getUsername());
                }
            }
            throw new RuntimeException("失效广告失败: " + e.getMessage(), e);
        }
    }

    /**
     * 结清广告
     *
     * @param advertisement 广告信息（包含收入和打款截图）
     * @return 结果
     */
    @Override
    public int clearanceAdvertisement(Advertisement advertisement) {
        advertisement.setLastUpdateTime(DateUtils.getNowDate());
        advertisement.setLastUpdater(SecurityUtils.getUsername());
        advertisement.setStatus(2); // 设置为已结清状态
        advertisement.setCleard(1); // 设置为已结清

        try {
            return advertisementMapper.clearanceAdvertisement(advertisement);
        } catch (Exception e) {
            // 如果是唯一约束冲突，尝试先删除相同广告位的已结清广告
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("uk_ad_position")) {
                Advertisement existingAd = advertisementMapper.selectAdvertisementById(advertisement.getId());
                if (existingAd != null) {
                    // 删除相同广告位的已结清广告
                    List<Advertisement> existingClearedAds = advertisementMapper.selectAdvertisementList(
                            new Advertisement() {
                                {
                                    setAdPosition(existingAd.getAdPosition());
                                    setStatus(2); // 已结清状态
                                }
                            });

                    for (Advertisement clearedAd : existingClearedAds) {
                        if (!clearedAd.getId().equals(advertisement.getId())) {
                            advertisementMapper.deleteAdvertisementById(clearedAd.getId());
                        }
                    }

                    // 重新尝试结清操作
                    return advertisementMapper.clearanceAdvertisement(advertisement);
                }
            }
            throw new RuntimeException("结清广告失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取广告统计数据
     *
     * @return 统计数据
     */
    @Override
    public AdvertisementStatistics getAdvertisementStatistics() {
        return advertisementMapper.getAdvertisementStatistics();
    }
}