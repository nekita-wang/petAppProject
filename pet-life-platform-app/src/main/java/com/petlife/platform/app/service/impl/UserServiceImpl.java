package com.petlife.platform.app.service.impl;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.enums.GrantTypeEnum;
import com.petlife.platform.app.auth.provider.CompositeTokenGranterContext;
import com.petlife.platform.app.auth.provider.token.AbstractTokenGranter;
import com.petlife.platform.app.enums.UserProfileExceptionCode;
import com.petlife.platform.app.mapper.StatusInfoMapper;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.app.service.UserService;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.enums.UserType;
import com.petlife.platform.common.pojo.dto.UserProfileRegisterDTO;
import com.petlife.platform.common.pojo.entity.StatusInfo;
import com.petlife.platform.common.pojo.entity.User;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.PasswordStrengthUtils;
import com.petlife.platform.common.utils.SecurityUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.common.utils.sign.RsaUtils;
import com.petlife.platform.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StatusInfoMapper statusInfoMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CompositeTokenGranterContext granterContext;


    @Override
    public ResponseData<AuthUserInfo> registerProfile(UserProfileRegisterDTO dto) {
        // 只处理个人资料注册逻辑，不处理宠物信息
        String phone = dto.getPhone();
        // 移除手动判空和格式校验，交由DTO注解和全局异常处理
        if (dto.getGender() == null || dto.getGender() < 0 || dto.getGender() > 2) {
            log.warn("性别字段非法: {}", dto.getGender());
            return ResponseData.error(400, "性别字段非法");
        }
        User existingUser = userMapper.selectByPhone(phone);
        if (existingUser != null) {
            log.warn("手机号已注册: {}", phone);
            return ResponseData.error(AuthExceptionCode.ACCOUNT_ALREADY_EXISTS);
        }
        int nickNameCount = userMapper.countByNickname(dto.getNickName());
        if (nickNameCount > 0) {
            log.warn("昵称已存在: {}", dto.getNickName());
            return ResponseData.error(AuthExceptionCode.USER_NICKNAME_EXIST);
        }
        String rawPassword;
        try {
            String privateKey = RsaUtils.getPrivateKey();
            if (privateKey == null || privateKey.isEmpty()) {
                log.error("RSA私钥为空，无法解密密码");
                return ResponseData.error(500, "RSA密钥对未初始化，请重启应用");
            }
            log.info("开始解密密码，加密密码长度: {}", dto.getPassword().length());
            rawPassword = RsaUtils.decryptByPrivateKey(dto.getPassword());
            log.info("密码解密成功，明文密码长度: {}", rawPassword.length());
        } catch (Exception e) {
            log.error("密码解密失败: {}", e.getMessage(), e);
            return ResponseData.error(AuthExceptionCode.PASSWORD_DECRYPT_ERROR);
        }
        PasswordStrengthUtils.PasswordStrengthResult strengthResult = PasswordStrengthUtils.validatePassword(rawPassword);
        if (!strengthResult.isValid()) {
            log.warn("密码强度不足: {}", String.join(", ", strengthResult.getErrors()));
            return ResponseData.error(AuthExceptionCode.PASSWORD_TOO_WEAK.getCode(),
                    "密码强度不足：" + String.join("，", strengthResult.getErrors()));
        }
        LocalDate birthday;
        try {
            birthday = LocalDate.parse(dto.getBirthday());
        } catch (Exception e) {
            log.warn("生日格式不正确: {}", dto.getBirthday());
            return ResponseData.error(400, "生日格式不正确，应为yyyy-MM-dd");
        }
        int age = Period.between(birthday, LocalDate.now()).getYears();
        if (age < 18) {
            log.warn("未满18周岁禁止注册: birthday={}, age={}", dto.getBirthday(), age);
            return ResponseData.error(400, "未满18周岁禁止注册");
        }
        byte minor = (byte) (age < 18 ? 1 : 0);
        String avatarUrl = dto.getAvatarUrl();
        if (avatarUrl != null && (avatarUrl.startsWith("http://") || avatarUrl.startsWith("https://"))) {
            int idx = avatarUrl.indexOf("/profile");
            if (idx != -1) {
                avatarUrl = avatarUrl.substring(idx);
            }
        }
        User newUser = new User();
        newUser.setPhone(phone);
        newUser.setNickName(dto.getNickName());
        newUser.setPassword(SecurityUtils.encryptPassword(rawPassword));
        newUser.setBirthday(birthday);
        newUser.setGender(dto.getGender());
        newUser.setMinor(minor);
        newUser.setStatus((byte) 0);
        newUser.setAvatarUrl(avatarUrl);
        try {
            userMapper.insert(newUser);
            log.info("新用户注册成功: phone={}, nickName={}", phone, dto.getNickName());
            StatusInfo statusInfo = new StatusInfo();
            statusInfo.setUserId(newUser.getUserId());
            statusInfo.setChangeType(0);
            statusInfo.setOldStatus(1);
            statusInfo.setNewStatus(0);
            statusInfo.setChangeTime(new java.util.Date());
            statusInfo.setOperatorType(0);
            statusInfo.setReason("注册激活");
            statusInfo.setProof(null);
            statusInfoMapper.insert(statusInfo);
            AbstractTokenGranter tokenGranter = (AbstractTokenGranter) granterContext.getGranter(GrantTypeEnum.PHONE);
            tokenGranter.insertLoginLog(tokenGranter.buildLoginLog(newUser.getUserId(), 0, 0, 0, null, null));
        } catch (Exception e) {
            log.error("用户注册失败", e);
            return ResponseData.error(AuthExceptionCode.REGISTER_FAILED);
        }
        AuthUserInfo authUserInfo = generateAuthUserInfo(newUser);
        authUserInfo.setNewUser(true);
        authUserInfo.setNeedPetInfo(true); // 只注册用户，未注册宠物
        return ResponseData.ok(authUserInfo);
    }

    private AuthUserInfo generateAuthUserInfo(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setAppUser(user);
        loginUser.setUserId(user.getUserId());
        loginUser.setUserType(UserType.APP_USER);
        String token = tokenService.createToken(loginUser);
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(user.getUserId());
        authUserInfo.setToken(token);
        authUserInfo.setExpire((long) tokenService.getAppExpireTime() * 60);
        log.info("生成token成功，userId={}, expire={}秒", user.getUserId(), authUserInfo.getExpire());
        return authUserInfo;
    }
}