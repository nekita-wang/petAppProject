package com.petlife.platform.web.controller.common;

import com.petlife.platform.common.config.RuoYiConfig;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.exception.file.InvalidExtensionException;
import com.petlife.platform.common.utils.file.FileUploadUtils;
import com.petlife.platform.common.utils.file.MimeTypeUtils;
import com.petlife.platform.common.utils.sign.RsaUtils;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.utils.ServletUtils;
import com.petlife.platform.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.petlife.platform.common.core.domain.AjaxResult;

import java.io.IOException;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private TokenService tokenService;

    /**
     * APP端图片上传接口（仅APP用户可用）
     */
    @PostMapping("/app/upload/avatar")
    public AjaxResult uploadAppAvatar(@RequestParam("avatarfile") MultipartFile file) throws IOException, InvalidExtensionException {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (loginUser == null || loginUser.getUserType() == null || !"APP_USER".equals(loginUser.getUserType().name())) {
            return AjaxResult.error(401, "无权限：仅APP用户可用");
        }
        if (file.isEmpty()) {
            return AjaxResult.error("上传图片不能为空");
        }
    
        String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION, true);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("imgUrl", avatar);
        return ajax;
     
    }

    /**
     * 后台管理系统图片上传接口（仅SYS_USER可用）
     */
    @PostMapping("/admin/upload/avatar")
    public AjaxResult uploadAdminAvatar(@RequestParam("avatarfile") MultipartFile file) throws IOException, InvalidExtensionException {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (loginUser == null || loginUser.getUserType() == null || !"SYS_USER".equals(loginUser.getUserType().name())) {
            return AjaxResult.error(401, "无权限：仅后台管理员可用");
        }
        if (file.isEmpty()) {
            return AjaxResult.error("上传图片不能为空");
        }
        String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION, true);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("imgUrl", avatar);
        return ajax;
    }

    /**
     * 获取公钥
     */
    @GetMapping("/publicKey")
    public AjaxResult getPublicKey() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("publicKey", RsaUtils.getPublicKey());
        return ajax;
    }
}
