package com.petlife.platform.app.controller;



import com.petlife.platform.common.config.RuoYiConfig;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.core.domain.R;
import com.petlife.platform.common.pojo.dto.PetBreedQuery;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.dto.PetInfoQuery;
import com.petlife.platform.common.pojo.vo.PetClassVo;
import com.petlife.platform.common.utils.file.FileUploadUtils;
import com.petlife.platform.common.utils.file.FileUtils;
import com.petlife.platform.framework.config.ServerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/app/pet")
@Api(tags = "宠物模块")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private ServerConfig serverConfig;


    /**
     * 您养的宠物
     * @param petInfoQuery
     * @return
     */
    @PostMapping("/addPet")
    @ApiOperation(value = "添加宠物信息", notes = "新用户注册后可选择添加宠物")
    public ResponseData<Void> addPet( @Validated @RequestBody PetInfoQuery petInfoQuery) {
        petService.addPetInfo(petInfoQuery);
        return ResponseData.ok();
    }

    /**
     * 宠物查询
     * @param petBreedQuery
     * @return
     */
    @GetMapping("/breeds")
    public R list( PetBreedQuery petBreedQuery)
    {
        Map<String, Object> map = petService.selectPetBreedAppList(petBreedQuery);
        return R.ok(map);
    }


    /**
     * 查询宠物类别
     */
    @GetMapping("/pet")
    public R petList()
    {
        List<PetClassVo> petClassVos = petService.selectPetClass();
        return R.ok(petClassVos);
    }


    /**
     * 图片上传
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            System.out.println(filePath);
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
