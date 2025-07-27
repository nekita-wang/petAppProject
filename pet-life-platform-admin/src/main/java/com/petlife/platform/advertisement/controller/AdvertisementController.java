package com.petlife.platform.advertisement.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petlife.platform.common.annotation.Log;
import com.petlife.platform.common.annotation.Anonymous;
import com.petlife.platform.common.core.controller.BaseController;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.enums.BusinessType;
import com.petlife.platform.advertisement.domain.Advertisement;
import com.petlife.platform.advertisement.service.IAdvertisementService;
import com.petlife.platform.common.utils.poi.ExcelUtil;
import com.petlife.platform.common.core.page.TableDataInfo;

/**
 * 广告信息Controller
 * 
 * @author petlife
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/advertisement/advertisement")
public class AdvertisementController extends BaseController {
    @Autowired
    private IAdvertisementService advertisementService;

    /**
     * 查询广告信息列表
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:list')")
    @GetMapping("/list")
    public TableDataInfo list(Advertisement advertisement) {
        startPage();
        List<Advertisement> list = advertisementService.selectAdvertisementList(advertisement);
        return getDataTable(list);
    }

    /**
     * 导出广告信息列表
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:export')")
    @Log(title = "广告信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Advertisement advertisement) {
        List<Advertisement> list = advertisementService.selectAdvertisementList(advertisement);
        ExcelUtil<Advertisement> util = new ExcelUtil<Advertisement>(Advertisement.class);
        util.exportExcel(response, list, "广告信息数据");
    }

    /**
     * 获取广告信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(advertisementService.selectAdvertisementById(id));
    }

    /**
     * 新增广告信息
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:add')")
    @Log(title = "广告信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Advertisement advertisement) {
        // 校验广告位唯一性
        if (!"0".equals(advertisementService.checkAdPositionUnique(advertisement))) {
            return AjaxResult.error("新增广告失败，该广告位已存在运行中的广告");
        }
        return toAjax(advertisementService.insertAdvertisement(advertisement));
    }

    /**
     * 修改广告信息
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:edit')")
    @Log(title = "广告信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Advertisement advertisement) {
        // 检查广告是否存在
        Advertisement existingAd = advertisementService.selectAdvertisementById(advertisement.getId());
        if (existingAd == null) {
            return AjaxResult.error("广告不存在");
        }

        // 状态流转验证：只有运行中的广告才能编辑
        if (existingAd.getStatus() != 1) {
            return AjaxResult.error("只有运行中的广告才能编辑");
        }

        // 校验广告位唯一性（状态为运行中时）
        if (advertisement.getStatus() == 1 && !"0".equals(advertisementService.checkAdPositionUnique(advertisement))) {
            return AjaxResult.error("修改广告失败，该广告位已存在运行中的广告");
        }
        return toAjax(advertisementService.updateAdvertisement(advertisement));
    }

    /**
     * 删除广告信息
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:remove')")
    @Log(title = "广告信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(advertisementService.deleteAdvertisementByIds(ids));
    }

    /**
     * 获取广告位下拉选项
     */
    @GetMapping("/adPositionOptions")
    public AjaxResult getAdPositionOptions() {
        // 返回广告位1-6的选项
        List<Object> options = new ArrayList<>();
        options.add(new Object[] { "1", "广告位1" });
        options.add(new Object[] { "2", "广告位2" });
        options.add(new Object[] { "3", "广告位3" });
        options.add(new Object[] { "4", "广告位4" });
        options.add(new Object[] { "5", "广告位5" });
        options.add(new Object[] { "6", "广告位6" });
        return AjaxResult.success(options);
    }

    /**
     * 根据广告位查询正在运行的广告（供前端展示使用）
     */
    @Anonymous
    @GetMapping("/running/{adPosition}")
    public AjaxResult getRunningAdvertisement(@PathVariable("adPosition") String adPosition) {
        Advertisement advertisement = advertisementService.selectRunningAdvertisementByPosition(adPosition);
        if (advertisement != null) {
            // 更新点击量
            advertisementService.updateClickCount(advertisement.getId());
        }
        return AjaxResult.success(advertisement);
    }

    /**
     * 失效广告
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:edit')")
    @Log(title = "失效广告", businessType = BusinessType.UPDATE)
    @PutMapping("/invalidate/{id}")
    public AjaxResult invalidateAdvertisement(@PathVariable("id") Integer id) {
        Advertisement advertisement = advertisementService.selectAdvertisementById(id);
        if (advertisement == null) {
            return AjaxResult.error("广告不存在");
        }

        // 只有运行中的广告才能失效
        if (advertisement.getStatus() != 1) {
            return AjaxResult.error("只有运行中的广告才能失效");
        }

        return toAjax(advertisementService.invalidateAdvertisement(id));
    }

    /**
     * 结清广告
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:edit')")
    @Log(title = "结清广告", businessType = BusinessType.UPDATE)
    @PutMapping("/clearance")
    public AjaxResult clearanceAdvertisement(@RequestBody Advertisement advertisement) {
        if (advertisement.getId() == null) {
            return AjaxResult.error("广告ID不能为空");
        }

        Advertisement existingAd = advertisementService.selectAdvertisementById(advertisement.getId());
        if (existingAd == null) {
            return AjaxResult.error("广告不存在");
        }

        // 只有运行中的广告才能结清
        if (existingAd.getStatus() != 1) {
            return AjaxResult.error("只有运行中的广告才能结清");
        }

        // 验证必填字段
        if (advertisement.getAdRevenue() == null) {
            return AjaxResult.error("收入金额不能为空");
        }

        if (advertisement.getRevenueAttachment() == null || advertisement.getRevenueAttachment().trim().isEmpty()) {
            return AjaxResult.error("打款截图不能为空");
        }

        return toAjax(advertisementService.clearanceAdvertisement(advertisement));
    }

    /**
     * 获取广告统计数据
     */
    @PreAuthorize("@ss.hasPermi('advertisement:advertisement:list')")
    @GetMapping("/statistics")
    public AjaxResult getStatistics() {
        return AjaxResult.success(advertisementService.getAdvertisementStatistics());
    }
}