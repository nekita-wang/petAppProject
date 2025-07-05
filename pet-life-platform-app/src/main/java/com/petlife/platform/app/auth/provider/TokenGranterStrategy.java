package com.petlife.platform.app.auth.provider;

import com.petlife.platform.common.pojo.dto.LoginDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;

public interface TokenGranterStrategy {
    AuthUserInfo grant(LoginDTO loginDTO);
}