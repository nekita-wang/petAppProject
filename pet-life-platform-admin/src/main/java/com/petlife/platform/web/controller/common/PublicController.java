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
     * APP端头像上传接口（仅APP用户可用）
     *
     * 业务规则：
     * 1. 只允许上传1张头像图片
     * 2. 支持格式：JPG/PNG
     * 3. 文件大小：≤10MB
     * 4. 支持圆形裁剪
     */
    @PostMapping("/app/upload/avatar")
    public AjaxResult uploadAppAvatar(
            @RequestParam("avatarfile") MultipartFile file,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "cropInfo", required = false) String cropInfo,
            HttpServletRequest request)
            throws IOException, InvalidExtensionException {
        try {
            // ==================== 1. 严格限制只能上传1张图片 ====================
            AjaxResult multiFileCheck = validateSingleFileUpload(request, "avatarfile");
            if (multiFileCheck != null) {
                return multiFileCheck; // 如果检测到多文件上传，直接返回错误
            }

            // ==================== 2. 基础验证 ====================
            if (file == null || file.isEmpty()) {
                return AjaxResult.error(400, "请选择要上传的头像图片");
            }

            // ==================== 3. 头像专用验证 ====================
            AjaxResult validationResult = validateAvatarFile(file);
            if (validationResult != null) {
                return validationResult; // 验证失败
            }

            // ==================== 4. 记录上传信息 ====================
            String originalFilename = file.getOriginalFilename();
            log.info("APP端头像上传 - 用户ID: {}, 文件名: {}, 大小: {}KB",
                userId, originalFilename, file.getSize() / 1024);

            // ==================== 5. 执行文件上传 ====================
            String avatar = FileUploadUtils.upload(
                RuoYiConfig.getTempAvatarPath(),
                file,
                new String[]{"jpg", "jpeg", "png"}, // 严格限制格式
                true // 使用UUID文件名
            );

            // ==================== 6. 记录日志和返回结果 ====================
            // 记录详细的上传信息用于监控
            log.info("APP端头像上传成功 - 用户: {}, 原文件: {}, 大小: {}, 路径: {}",
                userId, originalFilename, formatFileSize(file.getSize()), avatar);

            // 如果有裁剪信息，记录下来
            if (cropInfo != null && !cropInfo.trim().isEmpty()) {
                log.info("收到裁剪信息: {}", cropInfo);
            }

            // 按照您要求的格式返回：data 直接是图片路径字符串
            return AjaxResult.success("头像上传成功", avatar);

        } catch (InvalidExtensionException e) {
            log.error("头像格式错误: {}", e.getMessage());
            return AjaxResult.error(400, "头像格式不支持，请上传 JPG 或 PNG 格式的图片");
        } catch (Exception e) {
            log.error("头像上传失败: {}", e.getMessage(), e);
            return AjaxResult.error(500, "头像上传失败，请重试：" + e.getMessage());
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

    // ==================== 私有验证方法 ====================

    /**
     * 验证是否为单文件上传（防止多文件上传）
     *
     * @param request HTTP请求
     * @param paramName 参数名
     * @return 如果检测到多文件上传返回错误结果，否则返回null
     */
    private AjaxResult validateSingleFileUpload(HttpServletRequest request, String paramName) {
        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                List<MultipartFile> files = multipartRequest.getFiles(paramName);

                if (files != null && files.size() > 1) {
                    log.warn("检测到多文件上传尝试，文件数量: {}, 参数名: {}", files.size(), paramName);
                    return AjaxResult.error(400, "头像上传只允许选择1张图片，请重新选择");
                }

                // 检查是否有其他同名参数
                if (files != null && files.size() == 1) {
                    MultipartFile file = files.get(0);
                    if (file.isEmpty()) {
                        return AjaxResult.error(400, "上传的文件为空，请选择有效的图片文件");
                    }
                }
            }

            return null; // 验证通过
        } catch (Exception e) {
            log.error("多文件验证失败: {}", e.getMessage());
            return AjaxResult.error(500, "文件验证失败");
        }
    }

    /**
     * 验证头像文件的格式和大小
     *
     * @param file 上传的文件
     * @return 如果验证失败返回错误结果，否则返回null
     */
    private AjaxResult validateAvatarFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                return AjaxResult.error(400, "文件名不能为空");
            }

            // 1. 文件格式验证（头像只支持JPG/PNG）
            String fileExtension = getFileExtension(originalFilename).toLowerCase();
            if (!isValidAvatarFormat(fileExtension)) {
                return AjaxResult.error(400, "头像仅支持 JPG、JPEG、PNG 格式，当前格式：" + fileExtension.toUpperCase());
            }

            // 2. 文件大小验证
            long maxSize = 10 * 1024 * 1024; // 10MB
            if (file.getSize() > maxSize) {
                return AjaxResult.error(400, "头像文件大小不能超过10MB，当前大小：" + formatFileSize(file.getSize()));
            }

            // 3. 文件内容验证（检查文件头，防止伪造）
            if (!isValidImageContent(file)) {
                return AjaxResult.error(400, "文件内容不是有效的图片格式，请上传真实的图片文件");
            }

            // 4. 文件名安全检查
            if (containsUnsafeCharacters(originalFilename)) {
                return AjaxResult.error(400, "文件名包含非法字符，请重命名后重试");
            }

            return null; // 验证通过
        } catch (Exception e) {
            log.error("头像文件验证失败: {}", e.getMessage());
            return AjaxResult.error(500, "文件验证失败");
        }
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 验证是否为有效的头像格式
     */
    private boolean isValidAvatarFormat(String extension) {
        return "jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension);
    }

    /**
     * 验证文件内容是否为有效图片（检查文件头魔数）
     */
    private boolean isValidImageContent(MultipartFile file) {
        try {
            byte[] header = new byte[10];
            int bytesRead = file.getInputStream().read(header);
            if (bytesRead < 4) return false;

            // JPEG文件头: FF D8 FF
            if (header[0] == (byte) 0xFF && header[1] == (byte) 0xD8 && header[2] == (byte) 0xFF) {
                return true;
            }

            // PNG文件头: 89 50 4E 47
            if (header[0] == (byte) 0x89 && header[1] == 0x50 &&
                header[2] == 0x4E && header[3] == 0x47) {
                return true;
            }

            return false;
        } catch (Exception e) {
            log.error("文件内容验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 检查文件名是否包含不安全字符
     */
    private boolean containsUnsafeCharacters(String filename) {
        if (filename == null) return true;

        // 检查路径遍历攻击
        if (filename.contains("..") || filename.contains("/") ||
            filename.contains("\\") || filename.contains(":")) {
            return true;
        }

        // 检查其他危险字符
        String[] dangerousChars = {"<", ">", "|", "?", "*", "\"", "\0"};
        for (String dangerous : dangerousChars) {
            if (filename.contains(dangerous)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 格式化文件大小
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
