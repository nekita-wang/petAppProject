package com.petlife.platform.app.controller;

import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/app/user/manage")
@Api(tags = "用户管理模块")
public class UserManageController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/countByPhonePrefix")
    @ApiOperation(value = "根据手机号前缀统计用户数量", notes = "统计指定手机号前缀的用户数量")
    public ResponseData<Map<String, Object>> countByPhonePrefix(
            @ApiParam(value = "手机号前缀", required = true, example = "185162")
            @RequestParam String phonePrefix) {
        
        try {
            int count = userMapper.countByPhonePrefix(phonePrefix);
            Map<String, Object> result = new HashMap<>();
            result.put("phonePrefix", phonePrefix);
            result.put("userCount", count);
            
            log.info("统计手机号前缀 {} 的用户数量: {}", phonePrefix, count);
            return ResponseData.success(result);
        } catch (Exception e) {
            log.error("统计用户数量失败: {}", e.getMessage(), e);
            return ResponseData.error(500, "统计用户数量失败: " + e.getMessage());
        }
    }

    @GetMapping("/listByPhonePrefix")
    @ApiOperation(value = "根据手机号前缀查询用户详情", notes = "查询指定手机号前缀的用户详细信息")
    public ResponseData<Map<String, Object>> listByPhonePrefix(
            @ApiParam(value = "手机号前缀", required = true, example = "185162")
            @RequestParam String phonePrefix) {
        
        try {
            List<User> users = userMapper.selectByPhonePrefix(phonePrefix);
            Map<String, Object> result = new HashMap<>();
            result.put("phonePrefix", phonePrefix);
            result.put("users", users);
            result.put("userCount", users.size());
            
            log.info("查询手机号前缀 {} 的用户详情: {} 个", phonePrefix, users.size());
            return ResponseData.success(result);
        } catch (Exception e) {
            log.error("查询用户详情失败: {}", e.getMessage(), e);
            return ResponseData.error(500, "查询用户详情失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteByPhonePrefix")
    @ApiOperation(value = "根据手机号前缀删除用户", notes = "删除指定手机号前缀的所有用户")
    public ResponseData<Map<String, Object>> deleteByPhonePrefix(
            @ApiParam(value = "手机号前缀", required = true, example = "185162")
            @RequestParam String phonePrefix,
            @ApiParam(value = "确认删除", required = true, example = "true")
            @RequestParam(defaultValue = "false") boolean confirm) {
        
        if (!confirm) {
            return ResponseData.error(400, "请设置confirm=true来确认删除操作");
        }
        
        try {
            // 先统计要删除的用户数量
            int countBefore = userMapper.countByPhonePrefix(phonePrefix);
            
            if (countBefore == 0) {
                return ResponseData.error(404, "没有找到手机号前缀为 " + phonePrefix + " 的用户");
            }
            
            // 执行删除操作
            int deletedCount = userMapper.deleteByPhonePrefix(phonePrefix);
            
            Map<String, Object> result = new HashMap<>();
            result.put("phonePrefix", phonePrefix);
            result.put("deletedCount", deletedCount);
            result.put("message", "成功删除 " + deletedCount + " 个用户");
            
            log.warn("删除手机号前缀 {} 的用户: {} 个", phonePrefix, deletedCount);
            return ResponseData.success(result);
        } catch (Exception e) {
            log.error("删除用户失败: {}", e.getMessage(), e);
            return ResponseData.error(500, "删除用户失败: " + e.getMessage());
        }
    }
} 