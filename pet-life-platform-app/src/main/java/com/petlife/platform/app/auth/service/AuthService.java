package com.petlife.platform.app.auth.service;

import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.dto.SendCodeDTO;
import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;
import com.petlife.platform.common.utils.PasswordStrengthUtils;

public interface AuthService {
    ResponseData<String> sendLoginCode(SendCodeDTO dto);
    ResponseData<AuthUserInfo> login(LoginDTO loginDTO);
    ResponseData<PasswordStrengthUtils.PasswordStrengthResult> checkPasswordStrength(String password);
    ResponseData<String> getPublicKey();
    ResponseData<String> logout(String jwtToken);
} 