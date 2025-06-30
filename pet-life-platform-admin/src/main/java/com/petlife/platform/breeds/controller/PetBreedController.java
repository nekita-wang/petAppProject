package com.petlife.platform.breeds.controller;

import com.petlife.platform.breeds.domain.PetBreed;
import com.petlife.platform.breeds.service.IPetBreedService;
import com.petlife.platform.common.annotation.Log;
import com.petlife.platform.common.core.controller.BaseController;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.core.page.TableDataInfo;
import com.petlife.platform.common.enums.BusinessType;
import com.petlife.platform.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 宠物品种Controller
 *
 * @author ruoyi
 * @date 2025-06-27
 */
@RestController
@RequestMapping("/breed")
public class PetBreedController extends BaseController
{
    @Autowired
    private IPetBreedService petBreedService;

    /**
     * 查询宠物品种列表
     */
    @PreAuthorize("@ss.hasPermi('breed:breed:list')")
    @GetMapping("/list")
    public TableDataInfo list(PetBreed petBreed)
    {
        startPage();
        List<PetBreed> list = petBreedService.selectPetBreedList(petBreed);
        return getDataTable(list);
    }
    /**
     * 修改宠物状态
     */
    @PutMapping("/updateBreedStatus")
    public AjaxResult updateBreedStatus(@RequestBody Map<String, List<String>> request) {
        List<String> ids = request.get("ids");

        return toAjax(petBreedService.updateBreedStatus(ids));
    }
    /**
     * 导出宠物品种列表
     */
    @PreAuthorize("@ss.hasPermi('breed:breed:export')")
    @Log(title = "宠物品种", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PetBreed petBreed)
    {
        List<PetBreed> list = petBreedService.selectPetBreedList(petBreed);
        ExcelUtil<PetBreed> util = new ExcelUtil<PetBreed>(PetBreed.class);
        util.exportExcel(response, list, "宠物品种数据");
    }

    /**
     * 获取宠物品种详细信息
     */
    @PreAuthorize("@ss.hasPermi('breed:breed:query')")
    @GetMapping(value = "/{petBreedId}")
    public AjaxResult getInfo(@PathVariable("petBreedId") String petBreedId)
    {
        return success(petBreedService.selectPetBreedByPetClassId(petBreedId));
    }

    /**
     * 新增宠物品种
     */
    @PreAuthorize("@ss.hasPermi('breed:breed:add')")
    @Log(title = "宠物品种", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PetBreed petBreed)
    {
        try {
            int i = petBreedService.insertPetBreed(petBreed);
            return toAjax(i);
        }catch (Exception e){
            return error("宠物品种重复");
        }

    }

    /**
     * 修改宠物品种
     */
    @PreAuthorize("@ss.hasPermi('breed:breed:edit')")
    @Log(title = "宠物品种", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PetBreed petBreed)
    {
        try {
            int i = petBreedService.updatePetBreed(petBreed);
            return toAjax(i);
        }catch (Exception e){
            return error("宠物品种重复");
        }
    }

    /**
     * 删除宠物品种
     */
    @PreAuthorize("@ss.hasPermi('breed:breed:remove')")
    @Log(title = "宠物品种", businessType = BusinessType.DELETE)
	@DeleteMapping("/{petClassIds}")
    public AjaxResult remove(@PathVariable String[] petClassIds)
    {
        return toAjax(petBreedService.deletePetBreedByPetClassIds(petClassIds));
    }
}
