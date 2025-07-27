package com.petlife.platform.advertisement.service;

import java.util.List;
import com.petlife.platform.advertisement.domain.Advertisement;
import com.petlife.platform.advertisement.domain.AdvertisementStatistics;

/**
 * 广告信息Service接口
 * 
 * @author petlife
 * @date 2025-01-02
 */
public interface IAdvertisementService {
    /**
     * 查询广告信息
     * 
     * @param id 广告信息主键
     * @return 广告信息
     */
    public Advertisement selectAdvertisementById(Integer id);

    /**
     * 查询广告信息列表
     * 
     * @param advertisement 广告信息
     * @return 广告信息集合
     */
    public List<Advertisement> selectAdvertisementList(Advertisement advertisement);

    /**
     * 新增广告信息
     * 
     * @param advertisement 广告信息
     * @return 结果
     */
    public int insertAdvertisement(Advertisement advertisement);

    /**
     * 修改广告信息
     * 
     * @param advertisement 广告信息
     * @return 结果
     */
    public int updateAdvertisement(Advertisement advertisement);

    /**
     * 批量删除广告信息
     * 
     * @param ids 需要删除的广告信息主键集合
     * @return 结果
     */
    public int deleteAdvertisementByIds(Integer[] ids);

    /**
     * 删除广告信息信息
     * 
     * @param id 广告信息主键
     * @return 结果
     */
    public int deleteAdvertisementById(Integer id);

    /**
     * 根据广告位查询正在运行的广告
     * 
     * @param adPosition 广告位标识
     * @return 广告信息
     */
    public Advertisement selectRunningAdvertisementByPosition(String adPosition);

    /**
     * 更新广告点击量
     * 
     * @param id 广告ID
     * @return 结果
     */
    public int updateClickCount(Integer id);

    /**
     * 更新广告结清状态
     * 
     * @param id          广告ID
     * @param cleard      结清状态
     * @param lastUpdater 更新人
     * @return 结果
     */
    public int updateCleardStatus(Integer id, Integer cleard, String lastUpdater);

    /**
     * 校验广告位是否已存在运行中的广告
     *
     * @param advertisement 广告信息
     * @return 结果
     */
    public String checkAdPositionUnique(Advertisement advertisement);

    /**
     * 失效广告
     *
     * @param id 广告ID
     * @return 结果
     */
    public int invalidateAdvertisement(Integer id);

    /**
     * 结清广告
     *
     * @param advertisement 广告信息（包含收入和打款截图）
     * @return 结果
     */
    public int clearanceAdvertisement(Advertisement advertisement);

    /**
     * 获取广告统计数据
     *
     * @return 统计数据
     */
    public AdvertisementStatistics getAdvertisementStatistics();
}