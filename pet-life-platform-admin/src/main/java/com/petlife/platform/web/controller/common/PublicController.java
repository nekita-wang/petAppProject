package com.petlife.platform.web.controller.common;

import com.petlife.platform.common.annotation.Anonymous;
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

import java.io.IOException;

@RestController
@RequestMapping("/public")
@Anonymous
public class PublicController {
    @Autowired
    private TokenService tokenService;

    /**
     * APP端图片上传接口（仅APP用户可用）
     */
    @PostMapping("/app/upload/avatar")
    public AjaxResult uploadAppAvatar(@RequestParam("avatarfile") MultipartFile file)
            throws IOException, InvalidExtensionException {
        try {
            if (file.isEmpty()) {
                return AjaxResult.error(400, "上传图片不能为空");
            }

            // 验证文件类型和大小
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB限制
                return AjaxResult.error(400, "图片大小不能超过10MB");
            }

            // 上传到临时目录
            String avatar = FileUploadUtils.upload(RuoYiConfig.getTempAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION,
                    true);

            // 返回标准格式：msg, code, success, data
            return AjaxResult.success("上传成功", avatar);

        } catch (Exception e) {
            return AjaxResult.error(500, "上传失败：" + e.getMessage());
        }
    }

    /**
     * 后台管理系统图片上传接口（仅SYS_USER可用）
     */
    @PostMapping("/admin/upload/avatar")
    public AjaxResult uploadAdminAvatar(@RequestParam("avatarfile") MultipartFile file)
            throws IOException, InvalidExtensionException {
        try {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (loginUser == null || loginUser.getUserType() == null
                    || !"SYS_USER".equals(loginUser.getUserType().name())) {
                return AjaxResult.error(401, "无权限：仅后台管理员可用");
            }

            if (file.isEmpty()) {
                return AjaxResult.error(400, "上传图片不能为空");
            }

            // 验证文件类型和大小
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB限制
                return AjaxResult.error(400, "图片大小不能超过10MB");
            }

            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION,
                    true);

            // 返回标准格式：msg, code, success, data
            return AjaxResult.success("上传成功", avatar);

        } catch (Exception e) {
            return AjaxResult.error(500, "上传失败：" + e.getMessage());
        }
    }

    /**
     * 后台管理系统广告图片上传接口（仅SYS_USER可用）
     */
    @PostMapping("/admin/upload/advertisement")
    public AjaxResult uploadAdminAdvertisement(@RequestParam("avatarfile") MultipartFile file)
            throws IOException, InvalidExtensionException {
        try {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (loginUser == null || loginUser.getUserType() == null
                    || !"SYS_USER".equals(loginUser.getUserType().name())) {
                return AjaxResult.error(401, "无权限：仅后台管理员可用");
            }

            if (file.isEmpty()) {
                return AjaxResult.error(400, "上传图片不能为空");
            }

            // 验证文件类型和大小
            if (file.getSize() > 10 * 1024 * 1024) { // 10MB限制
                return AjaxResult.error(400, "图片大小不能超过10MB");
            }

            String advertisement = FileUploadUtils.upload(RuoYiConfig.getAdvertisementPath(), file,
                    MimeTypeUtils.IMAGE_EXTENSION, true);

            // 返回标准格式：msg, code, success, data
            return AjaxResult.success("上传成功", advertisement);

        } catch (Exception e) {
            return AjaxResult.error(500, "上传失败：" + e.getMessage());
        }
    }

    /**
     * 获取公钥
     */
    @GetMapping("/publicKey")
    public AjaxResult getPublicKey() {
        try {
            String publicKey = RsaUtils.getPublicKey();
            if (publicKey == null || publicKey.isEmpty()) {
                return AjaxResult.error(500, "RSA公钥未初始化，请重启应用");
            }
            // 返回标准格式：msg, code, success, data
            return AjaxResult.success("操作成功", publicKey);
        } catch (Exception e) {
            return AjaxResult.error(500, "获取公钥失败");
        }
    }
}
