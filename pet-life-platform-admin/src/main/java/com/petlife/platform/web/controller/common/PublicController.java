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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Anonymous
public class PublicController {

    private static final Logger log = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private TokenService tokenService;

    /**
     * APP端头像上传接口
     *
     * 业务需求：
     * 1. 图片大小≤10MB
     * 2. 1次只允许上传1张图片（MultipartFile file参数天然支持）
     * 3. 支持图片格式（基于若依框架MimeTypeUtils.IMAGE_EXTENSION）
     * 4. 支持圆形裁剪信息记录
     */
    @PostMapping("/app/upload/avatar")
    public AjaxResult uploadAppAvatar(
            @RequestParam("avatarfile") MultipartFile file,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "cropInfo", required = false) String cropInfo,
            HttpServletRequest request)
            throws IOException, InvalidExtensionException {
        try {
            // 1. 检测多文件上传（业务需求：只允许上传1张图片）
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                List<MultipartFile> files = multipartRequest.getFiles("avatarfile");

                if (files != null && files.size() > 1) {
                    log.warn("检测到多文件上传尝试，文件数量: {}, 用户: {}", files.size(), userId);
                    return AjaxResult.error(400, "头像上传只允许选择1张图片，当前选择了" + files.size() + "张，请重新选择");
                }
            }

            // 2. 基础验证
            if (file.isEmpty()) {
                return AjaxResult.error(400, "请选择要上传的头像图片");
            }

            // 3. 业务需求：文件大小≤10MB
            long maxSize = 10 * 1024 * 1024; // 10MB
            if (file.getSize() > maxSize) {
                return AjaxResult.error(400, "头像文件大小不能超过10MB，当前大小：" + formatFileSize(file.getSize()));
            }

            // 4. 使用若依框架进行文件上传和验证
            // 注意：MultipartFile file 参数天然只接收1个文件，满足"1次只允许上传1张图片"的需求
            // 若依框架会自动进行：
            // - 图片格式验证（bmp, gif, jpg, jpeg, png）
            // - 文件名长度验证
            // - 文件内容验证
            String avatar = FileUploadUtils.upload(
                    RuoYiConfig.getTempAvatarPath(),
                    file,
                    MimeTypeUtils.IMAGE_EXTENSION, // 若依定义的图片格式：bmp, gif, jpg, jpeg, png
                    true // 使用UUID文件名
            );

            // 5. 记录上传日志
            log.info("APP端头像上传成功 - 用户: {}, 文件: {}, 大小: {}, 路径: {}",
                    userId, file.getOriginalFilename(), formatFileSize(file.getSize()), avatar);

            // 6. 记录裁剪信息（如果有）
            if (cropInfo != null && !cropInfo.trim().isEmpty()) {
                log.info("收到裁剪信息: {}", cropInfo);
            }

            // 7. 返回简洁格式：data直接是图片路径
            return AjaxResult.success("头像上传成功", avatar);

        } catch (InvalidExtensionException e) {
            log.error("头像格式错误: {}", e.getMessage());
            return AjaxResult.error(400, "头像格式不支持，请上传图片格式文件");
        } catch (Exception e) {
            log.error("头像上传失败: userId={}, error={}", userId, e.getMessage(), e);
            return AjaxResult.error(500, "头像上传失败：" + e.getMessage());
        }
    }

    /**
     * 管理端头像上传接口
     *
     * 使用若依框架现有实现，保持一致性
     */
    @PostMapping("/admin/upload/avatar")
    public AjaxResult uploadAdminAvatar(@RequestParam("avatarfile") MultipartFile file)
            throws IOException, InvalidExtensionException {
        try {
            // 权限验证
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (loginUser == null || !"SYS_USER".equals(loginUser.getUserType().name())) {
                return AjaxResult.error(401, "无权限：仅后台管理员可用");
            }

            if (file.isEmpty()) {
                return AjaxResult.error(400, "上传图片不能为空");
            }

            // 使用若依框架的标准实现
            String avatar = FileUploadUtils.upload(
                    RuoYiConfig.getAvatarPath(),
                    file,
                    MimeTypeUtils.IMAGE_EXTENSION,
                    true
            );

            log.info("管理端头像上传成功 - 用户: {}, 路径: {}", loginUser.getUsername(), avatar);
            return AjaxResult.success("头像上传成功", avatar);

        } catch (InvalidExtensionException e) {
            log.error("管理端头像格式错误: {}", e.getMessage());
            return AjaxResult.error(400, "头像格式不支持，请上传图片格式文件");
        } catch (Exception e) {
            log.error("管理端头像上传失败: {}", e.getMessage(), e);
            return AjaxResult.error(500, "头像上传失败：" + e.getMessage());
        }
    }

    /**
     * 广告图片上传接口
     *
     * 保持现有实现
     */
    @PostMapping("/admin/upload/advertisement")
    public AjaxResult uploadAdminAdvertisement(@RequestParam("avatarfile") MultipartFile file)
            throws IOException, InvalidExtensionException {
        try {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (loginUser == null || !"SYS_USER".equals(loginUser.getUserType().name())) {
                return AjaxResult.error(401, "无权限：仅后台管理员可用");
            }

            if (file.isEmpty()) {
                return AjaxResult.error(400, "上传图片不能为空");
            }

            String advertisement = FileUploadUtils.upload(
                    RuoYiConfig.getAdvertisementPath(),
                    file,
                    MimeTypeUtils.IMAGE_EXTENSION,
                    true
            );

            log.info("广告图片上传成功 - 管理员: {}, 路径: {}", loginUser.getUsername(), advertisement);
            return AjaxResult.success("上传成功", advertisement);

        } catch (Exception e) {
            log.error("广告图片上传失败: {}", e.getMessage(), e);
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
            return AjaxResult.success("操作成功", publicKey);
        } catch (Exception e) {
            log.error("获取公钥失败: {}", e.getMessage());
            return AjaxResult.error(500, "获取公钥失败");
        }
    }

    /**
     * 格式化文件大小
     *
     * @param size 文件大小（字节）
     * @return 格式化后的文件大小字符串
     */
    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.1f KB", size / 1024.0);
        } else {
            return String.format("%.1f MB", size / (1024.0 * 1024.0));
        }
    }
}
