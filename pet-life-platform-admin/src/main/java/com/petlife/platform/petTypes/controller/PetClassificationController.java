package com.petlife.platform.petTypes.controller;

import com.petlife.platform.common.annotation.Log;
import com.petlife.platform.common.core.controller.BaseController;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.core.domain.R;
import com.petlife.platform.common.core.page.TableDataInfo;
import com.petlife.platform.common.enums.BusinessType;
import com.petlife.platform.common.utils.poi.ExcelUtil;
import com.petlife.platform.petTypes.domain.PetClassVo;
import com.petlife.platform.petTypes.domain.PetClassification;
import com.petlife.platform.petTypes.service.IPetClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 宠物分类Controller
 *
 * @author ruoyi
 * @date 2025-06-27
 */
@RestController
@RequestMapping("/petType")
public class PetClassificationController extends BaseController
{
    @Autowired
    private IPetClassificationService petClassificationService;

    /**
     * 查询宠物分类列表
     */
    @PreAuthorize("@ss.hasPermi('petType:petType:list')")
    @GetMapping("/list")
    public TableDataInfo list(PetClassification petClassification)
    {
        startPage();
        List<PetClassification> list = petClassificationService.selectPetClassificationList(petClassification);
        return getDataTable(list);
    }

    /**
     * 查询宠物类别
     */
    @GetMapping("/pet")
    public R petList()
    {
        List<PetClassVo> petClassVos = petClassificationService.selectPetClass();
        return R.ok(petClassVos);
    }

    /**
     * 导出宠物分类列表
     */
    @PreAuthorize("@ss.hasPermi('petType:petType:export')")
    @Log(title = "宠物分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PetClassification petClassification)
    {
        List<PetClassification> list = petClassificationService.selectPetClassificationList(petClassification);
        ExcelUtil<PetClassification> util = new ExcelUtil<PetClassification>(PetClassification.class);
        util.exportExcel(response, list, "宠物分类数据");
    }

    /**
     * 获取宠物分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('petType:petType:query')")
    @GetMapping(value = "/{petClassId}")
    public AjaxResult getInfo(@PathVariable("petClassId") String petClassId)
    {
        return success(petClassificationService.selectPetClassificationByPetClassId(petClassId));
    }

    /**
     * 新增宠物分类
     */
    @PreAuthorize("@ss.hasPermi('petType:petType:add')")
    @Log(title = "宠物分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PetClassification petClassification)
    {
        try {
            int i = petClassificationService.insertPetClassification(petClassification);
            return toAjax(i);
        }catch (Exception e){
            return error("宠物名或者宠物（英文）重复");
        }

    }

    /**
     * 修改宠物分类
     */
    @PreAuthorize("@ss.hasPermi('petType:petType:edit')")
    @Log(title = "宠物分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PetClassification petClassification)
    {
        try {
            int i = petClassificationService.updatePetClassification(petClassification);
            return toAjax(i);
        }catch (Exception e){
            return error("宠物名或者宠物（英文）重复");
        }

    }

    /**
     * 删除宠物分类
     */
    @PreAuthorize("@ss.hasPermi('petType:petType:remove')")
    @Log(title = "宠物分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{petClassIds}")
    public AjaxResult remove(@PathVariable String[] petClassIds)
    {
        return toAjax(petClassificationService.deletePetClassificationByPetClassIds(petClassIds));
    }

    /**
     * 失效
     * @param request
     * @return
     */
    @PutMapping("/updateStatusBatch")
    public AjaxResult updatePetTypeStatusBatch(@RequestBody Map<String, List<String>> request) {
        List<String> ids = request.get("ids");

        return toAjax(petClassificationService.petClassificationByPetClassIds(ids));
    }
}
