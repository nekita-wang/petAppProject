package com.petlife.platform.app.service;

import com.petlife.platform.common.core.api.ResponseData;
import com.petlife.platform.common.pojo.dto.UserProfileRegisterDTO;
import com.petlife.platform.common.pojo.vo.AuthUserInfo;

public interface UserService {

    ResponseData<AuthUserInfo> registerProfile(UserProfileRegisterDTO dto);
}
