package com.petlife.platform.app.mapper;

import com.petlife.platform.app.dto.AdvertisementAppDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * APP端广告Mapper接口
 *
 * @author petlife
 * @date 2025-07-27
 */
@Mapper
public interface AdvertisementAppMapper {

    /**
     * 根据广告位查询正在运行的广告（APP端专用）
     * 只返回前端必需的字段
     *
     * @param adPosition 广告位标识
     * @return 广告信息DTO
     */
    AdvertisementAppDto selectRunningAdvertisementByPosition(@Param("adPosition") String adPosition);

    /**
     * 更新广告点击量
     *
     * @param adPosition 广告位标识
     * @return 影响行数
     */
    int updateClickCountByPosition(@Param("adPosition") String adPosition);

    /**
     * 检查广告位是否有运行中的广告
     *
     * @param adPosition 广告位标识
     * @return 广告ID，如果没有则返回null
     */
    Integer checkRunningAdvertisementExists(@Param("adPosition") String adPosition);
}
