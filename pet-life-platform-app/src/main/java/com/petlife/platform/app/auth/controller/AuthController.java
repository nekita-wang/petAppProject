package com.petlife.platform.app.auth.controller;

import com.petlife.platform.app.auth.enums.AuthExceptionCode;
import com.petlife.platform.app.auth.enums.GrantTypeEnum;
import com.petlife.platform.app.auth.provider.CompositeTokenGranterContext;
import com.petlife.platform.app.auth.provider.token.AbstractTokenGranter;
import com.petlife.platform.app.mapper.UserMapper;
import com.petlife.platform.common.pojo.dto.SendCodeDTO;
import com.petlife.platform.common.pojo.entity.User;
import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.core.exception.PetException;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.ServletUtils;
import com.petlife.platform.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.petlife.platform.framework.web.service.TokenService;
import com.petlife.platform.common.pojo.dto.RegisterDTO;
import com.petlife.platform.common.pojo.dto.VerifyCodeDTO;
import com.petlife.platform.common.pojo.dto.StepRegisterDTO;
import com.petlife.platform.common.pojo.dto.PetInfoDTO;
import com.petlife.platform.app.service.PetService;
import com.petlife.platform.common.utils.sign.RsaUtils;
import com.petlife.platform.common.utils.SecurityUtils;
import com.petlife.platform.common.utils.PasswordStrengthUtils;
import java.time.LocalDate;
import org.springframework.validation.annotation.Validated;
import com.petlife.platform.common.enums.UserType;

import java.time.Duration;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RestController
@RequestMapping("/app/auth")
@Api(tags = "登录模块")
public class AuthController {

    @Autowired
    private CompositeTokenGranterContext granterContext;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PetService petService;

    /**
     * 发送登录验证码
     */
    @PostMapping("/sendCode")
    @ApiOperation(value = "发送验证码", notes = "手机号格式合法则发送验证码")
    public ResponseData<String> sendLoginCode(@RequestBody(required = false) SendCodeDTO dto) {
        String phone = dto.getPhone();
        if (!StringUtils.hasText(phone)) {
            log.warn("手机号不能为空");
            return ResponseData.error(AuthExceptionCode.PHONE_IS_EMPTY);
        }

        if (!AbstractTokenGranter.PHONE_PATTERN.matcher(phone).matches()) {
            log.warn("手机号格式不合法: {}", phone);
            return ResponseData.error(AuthExceptionCode.PHONE_FORMAT_ERROR);
        }

        // 如果手机号已注册，检查状态
        User user = userMapper.selectByPhone(phone);
        if (user != null) {
            switch (user.getStatus()) {
                case 1:
                    log.warn("账号已注销: userId={}", user.getUserId());
                    throw new PetException(AuthExceptionCode.ACCOUNT_CANCELLED);
                case 2:
                    log.warn("账号已冻结: userId={}", user.getUserId());
                    throw new PetException(AuthExceptionCode.ACCOUNT_FROZEN);
//                case 3:
//                    log.warn("账号被禁用: userId={}", user.getUserId());
//                    throw new PetException(AuthExceptionCode.ACCOUNT_DISABLED);
                default:
                    // 正常
            }
        }

        // 60 秒内不能重复发送
        String limitKey = AbstractTokenGranter.VERIFY_CODE_KEY_PREFIX + "limit:" + phone;
        Boolean success = redisTemplate.opsForValue().setIfAbsent(limitKey, "1", Duration.ofSeconds(60));
        if (Boolean.FALSE.equals(success)) {
            // 频繁发送验证码
            log.warn("发送验证码过于频繁: {}", phone);
            return ResponseData.error(AuthExceptionCode.CODE_SEND_TOO_FREQUENT);
        }

        // 生成 6 位验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        // 保存验证码，有效期 5 分钟
        redisTemplate.opsForValue().set(
                AbstractTokenGranter.VERIFY_CODE_KEY_PREFIX + phone,
                code,
                Duration.ofMinutes(5)
        );

        // TODO: 调用第三方短信服务发送验证码（暂时只打印）
        log.info("发送登录验证码: phone={}, code={}", phone, code);

        // 开发环境（临时返回code）：
        return ResponseData.ok(code);
    }

    /**
     * 登录接口：支持手机号密码登录和手机号验证码登录等
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录接口", notes = "支持手机号密码登录和手机号验证码登录")
    public ResponseData<AuthUserInfo> login(@RequestBody LoginDTO loginDTO) {
        // 从前端传入的 grantType（如 "pwdCaptcha"）找到枚举
        GrantTypeEnum grantType = GrantTypeEnum.getEnumByCode(loginDTO.getGrantType());
        if (Objects.isNull(grantType)) {
            throw new PetException(AuthExceptionCode.GRANTER_INEXISTENCE);
        }

        log.info("用户尝试登录，grantType={}, phone={}", grantType.getCode(), loginDTO.getPhone());

        // 根据不同策略执行登录
        AuthUserInfo authUserInfo = granterContext.getGranter(grantType).grant(loginDTO);

        return ResponseData.ok(authUserInfo);
    }

    /**
     * 验证码校验接口（分步注册第一步）
     */
    @PostMapping("/verifyCode")
    @ApiOperation(value = "验证码校验", notes = "分步注册第一步：校验验证码，不创建用户")
    public ResponseData<String> verifyCode(@Validated @RequestBody VerifyCodeDTO dto) {
        String phone = dto.getPhone();
        String code = dto.getCode();
        
        // 1️⃣ 检查手机号是否已注册
        User existingUser = userMapper.selectByPhone(phone);
        if (existingUser != null) {
            log.warn("手机号已注册: {}", phone);
            return ResponseData.error(AuthExceptionCode.ACCOUNT_ALREADY_EXISTS);
        }

        // 2️⃣ 校验验证码
        String redisKey = AbstractTokenGranter.VERIFY_CODE_KEY_PREFIX + phone;
        String cachedCode = redisTemplate.opsForValue().get(redisKey);
        if (cachedCode == null) {
            log.warn("验证码已过期或未发送: phone={}", phone);
            return ResponseData.error(AuthExceptionCode.CODE_EXPIRED);
        }
        if (!code.equals(cachedCode)) {
            log.warn("验证码不正确: 输入={}, 实际={}", code, cachedCode);
            return ResponseData.error(AuthExceptionCode.CODE_INCORRECT);
        }

        // 3️⃣ 生成临时token，用于后续注册步骤
        String tempToken = generateTempToken(phone);
        
        // 4️⃣ 删除验证码，防止重复使用
        redisTemplate.delete(redisKey);
        
        log.info("验证码校验成功: phone={}", phone);
        return ResponseData.ok(tempToken);
    }

    /**
     * 分步注册接口（分步注册第二步）
     */
    @PostMapping("/stepRegister")
    @ApiOperation(value = "分步注册", notes = "分步注册第二步：提交个人资料和可选的宠物信息")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData<AuthUserInfo> stepRegister(@Validated @RequestBody StepRegisterDTO dto) {
        String phone = dto.getPhone();
        
        // 1️⃣ 校验手机号格式
        if (!AbstractTokenGranter.PHONE_PATTERN.matcher(phone).matches()) {
            log.warn("手机号格式不合法: {}", phone);
            return ResponseData.error(AuthExceptionCode.PHONE_FORMAT_ERROR);
        }

        // 2️⃣ 检查手机号是否已注册
        User existingUser = userMapper.selectByPhone(phone);
        if (existingUser != null) {
            log.warn("手机号已注册: {}", phone);
            return ResponseData.error(AuthExceptionCode.ACCOUNT_ALREADY_EXISTS);
        }

        // 3️⃣ 检查昵称是否已存在
        int nickNameCount = userMapper.countByNickname(dto.getNickName());
        if (nickNameCount > 0) {
            log.warn("昵称已存在: {}", dto.getNickName());
            return ResponseData.error(AuthExceptionCode.USER_NICKNAME_EXIST);
        }

        // 4️⃣ 解密密码
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

        // 5️⃣ 校验密码强度
        PasswordStrengthUtils.PasswordStrengthResult strengthResult = PasswordStrengthUtils.validatePassword(rawPassword);
        if (!strengthResult.isValid()) {
            log.warn("密码强度不足: {}", String.join(", ", strengthResult.getErrors()));
            return ResponseData.error(AuthExceptionCode.PASSWORD_TOO_WEAK.getCode(), 
                "密码强度不足：" + String.join("，", strengthResult.getErrors()));
        }

        // 6️⃣ 创建新用户
        User newUser = new User();
        newUser.setPhone(phone);
        newUser.setNickName(dto.getNickName());
        newUser.setPassword(SecurityUtils.encryptPassword(rawPassword));
        newUser.setBirthday(LocalDate.parse(dto.getBirthday()));
        newUser.setGender(dto.getGender());
        newUser.setMinor(dto.getMinor());
        newUser.setStatus((byte) 0); // 正常状态
        newUser.setAvatarUrl(dto.getAvatarUrl());

        try {
            userMapper.insert(newUser);
            log.info("新用户注册成功: phone={}, nickName={}", phone, dto.getNickName());
        } catch (Exception e) {
            log.error("用户注册失败", e);
            return ResponseData.error(AuthExceptionCode.REGISTER_FAILED);
        }

        // 7️⃣ 处理宠物信息（可选）
        if (dto.getPets() != null && !dto.getPets().isEmpty()) {
            for (PetInfoDTO petInfo : dto.getPets()) {
                petInfo.setUserId(newUser.getUserId()); // 设置用户ID
                petService.addPetInfo(petInfo);
            }
            log.info("用户宠物信息添加成功: userId={}, petCount={}", newUser.getUserId(), dto.getPets().size());
        }

        // 8️⃣ 生成登录token
        AuthUserInfo authUserInfo = generateAuthUserInfo(newUser);
        authUserInfo.setNewUser(true);
        authUserInfo.setNeedPetInfo(dto.getPets() == null || dto.getPets().isEmpty()); // 如果没有宠物信息，则需要填写

        return ResponseData.ok(authUserInfo);
    }

    /**
     * 用户注册接口（保留原有接口，用于兼容）
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "手机号验证码注册新用户")
    public ResponseData<AuthUserInfo> register(@Validated @RequestBody RegisterDTO registerDTO) {
        String phone = registerDTO.getPhone();
        
        // 1️⃣ 校验手机号格式
        if (!AbstractTokenGranter.PHONE_PATTERN.matcher(phone).matches()) {
            log.warn("手机号格式不合法: {}", phone);
            return ResponseData.error(AuthExceptionCode.PHONE_FORMAT_ERROR);
        }

        // 2️⃣ 检查手机号是否已注册
        User existingUser = userMapper.selectByPhone(phone);
        if (existingUser != null) {
            log.warn("手机号已注册: {}", phone);
            return ResponseData.error(AuthExceptionCode.ACCOUNT_ALREADY_EXISTS);
        }

        // 3️⃣ 校验验证码
        String code = registerDTO.getCode();
        String redisKey = AbstractTokenGranter.VERIFY_CODE_KEY_PREFIX + phone;
        String cachedCode = redisTemplate.opsForValue().get(redisKey);
        if (cachedCode == null) {
            log.warn("验证码已过期或未发送: phone={}", phone);
            return ResponseData.error(AuthExceptionCode.CODE_EXPIRED);
        }
        if (!code.equals(cachedCode)) {
            log.warn("验证码不正确: 输入={}, 实际={}", code, cachedCode);
            return ResponseData.error(AuthExceptionCode.CODE_INCORRECT);
        }

        // 删除验证码
        redisTemplate.delete(redisKey);

        // 4️⃣ 检查昵称是否已存在
        int nickNameCount = userMapper.countByNickname(registerDTO.getNickName());
        if (nickNameCount > 0) {
            log.warn("昵称已存在: {}", registerDTO.getNickName());
            return ResponseData.error(AuthExceptionCode.USER_NICKNAME_EXIST);
        }

        // 5️⃣ 解密密码
        String rawPassword;
        try {
            // 检查RSA密钥对是否已初始化
            String privateKey = RsaUtils.getPrivateKey();
            if (privateKey == null || privateKey.isEmpty()) {
                log.error("RSA私钥为空，无法解密密码");
                return ResponseData.error(500, "RSA密钥对未初始化，请重启应用");
            }
            
            log.info("开始解密密码，加密密码长度: {}", registerDTO.getPassword().length());
            rawPassword = RsaUtils.decryptByPrivateKey(registerDTO.getPassword());
            log.info("密码解密成功，明文密码长度: {}", rawPassword.length());
        } catch (Exception e) {
            log.error("密码解密失败: {}", e.getMessage(), e);
            return ResponseData.error(AuthExceptionCode.PASSWORD_DECRYPT_ERROR);
        }

        // 6️⃣ 校验密码强度
        PasswordStrengthUtils.PasswordStrengthResult strengthResult = PasswordStrengthUtils.validatePassword(rawPassword);
        if (!strengthResult.isValid()) {
            log.warn("密码强度不足: {}", String.join(", ", strengthResult.getErrors()));
            return ResponseData.error(AuthExceptionCode.PASSWORD_TOO_WEAK.getCode(), 
                "密码强度不足：" + String.join("，", strengthResult.getErrors()));
        }

        // 7️⃣ 创建新用户
        User newUser = new User();
        newUser.setPhone(phone);
        newUser.setNickName(registerDTO.getNickName());
        newUser.setPassword(SecurityUtils.encryptPassword(rawPassword));
        newUser.setBirthday(LocalDate.parse(registerDTO.getBirthday()));
        newUser.setGender(registerDTO.getGender());
        newUser.setMinor(registerDTO.getMinor());
        newUser.setStatus((byte) 0); // 正常状态
        newUser.setAvatarUrl("/profile/avatar/2025/07/13/45c18bdf1af54a9aae36ac74f963e07d.jpeg"); // 默认头像

        try {
            userMapper.insert(newUser);
            log.info("新用户注册成功: phone={}, nickName={}", phone, registerDTO.getNickName());
        } catch (Exception e) {
            log.error("用户注册失败", e);
            return ResponseData.error(AuthExceptionCode.REGISTER_FAILED);
        }

        // 8️⃣ 生成登录token
        AuthUserInfo authUserInfo = generateAuthUserInfo(newUser);
        authUserInfo.setNewUser(true);
        authUserInfo.setNeedPetInfo(true); // 新用户需要填写宠物信息

        return ResponseData.ok(authUserInfo);
    }

    /**
     * 密码强度校验接口
     */
    @PostMapping("/checkPasswordStrength")
    @ApiOperation(value = "密码强度校验", notes = "实时校验密码强度")
    public ResponseData<PasswordStrengthUtils.PasswordStrengthResult> checkPasswordStrength(@RequestBody String password) {
        if (password == null || password.trim().isEmpty()) {
            return ResponseData.error(AuthExceptionCode.PARAMS_MISSING.getCode(), "密码不能为空");
        }
        
        PasswordStrengthUtils.PasswordStrengthResult result = PasswordStrengthUtils.validatePassword(password);
        return ResponseData.ok(result);
    }

    /**
     * 获取RSA公钥接口
     */
    @GetMapping("/getPublicKey")
    @ApiOperation(value = "获取RSA公钥", notes = "用于前端加密密码")
    public ResponseData<String> getPublicKey() {
        try {
            String publicKey = RsaUtils.getPublicKey();
            if (publicKey == null || publicKey.isEmpty()) {
                log.warn("RSA公钥为空，请检查RSA密钥对是否正确初始化");
                return ResponseData.error(500, "RSA公钥未初始化，请重启应用");
            }
            log.info("获取RSA公钥成功，长度: {}", publicKey.length());
            return ResponseData.ok(publicKey);
        } catch (Exception e) {
            log.error("获取RSA公钥失败", e);
            return ResponseData.error(500, "获取公钥失败");
        }
    }

    /**
     * 生成临时token，用于分步注册
     */
    private String generateTempToken(String phone) {
        // 生成一个简单的临时token，用于标识验证码校验通过
        String tempToken = "temp_" + phone + "_" + System.currentTimeMillis();
        // 将临时token存储到Redis，有效期10分钟
        redisTemplate.opsForValue().set("temp_token:" + tempToken, phone, Duration.ofMinutes(10));
        return tempToken;
    }

    /**
     * 生成JWT Token并缓存到Redis
     */
    private AuthUserInfo generateAuthUserInfo(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setAppUser(user);
        loginUser.setUserId(user.getUserId());
        loginUser.setUserType(UserType.APP_USER);
        String token = tokenService.createToken(loginUser);
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(user.getUserId());
        authUserInfo.setToken(token);
        authUserInfo.setExpire((long) tokenService.getAppExpireTime() * 60); // expireTime 是分钟，要转秒
        log.info("生成token成功，userId={}, expire={}秒", user.getUserId(), authUserInfo.getExpire());
        return authUserInfo;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "清理登录状态")
    public ResponseData<String> logout() {
        String jwtToken = ServletUtils.getRequest().getHeader("Authorization");

        if (StringUtils.isEmpty(jwtToken) || !jwtToken.startsWith("Bearer ")) {
            log.warn("退出登录时未获取到正确的JWT token: {}", jwtToken);
            return ResponseData.error(400,"未获取到正确的JWT token");
        }

        jwtToken = jwtToken.replace("Bearer ", "");

        try {
            boolean deleted = tokenService.delLoginUser(jwtToken);
            if (deleted) {
                log.info("退出登录成功，已删除 redis 中的 token");
                return ResponseData.ok("退出成功");
            } else {
                log.warn("退出登录时未找到 redis 中的 token");
                return ResponseData.ok("退出成功（但未找到有效会话）");
            }
        } catch (Exception e) {
            log.error("退出登录时解析 token 出错: {}", e.getMessage());
            return ResponseData.error(500, "退出登录失败，请稍后再试");
        }
    }
}
