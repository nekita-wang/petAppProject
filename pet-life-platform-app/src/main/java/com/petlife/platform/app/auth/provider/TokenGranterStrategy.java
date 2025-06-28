package com.petlife.platform.app.auth.provider;

import com.petlife.platform.app.pojo.dto.LoginDTO;
import com.petlife.platform.app.token.model.AuthUserInfo;

public interface TokenGranterStrategy {
    AuthUserInfo grant(LoginDTO loginDTO);
}