package com.petlife.platform.framework.interceptor;

import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户类型拦截器
 */
@Component
public class UserTypeInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // 白名单：这些接口不需要登录
        if (uri.startsWith("/app/auth/login")
                || uri.startsWith("/app/auth/sendCode")
                || uri.startsWith("/app/auth/logout")
                || uri.startsWith("/publicKey")) {
            return true;
        }

        // 其它接口需要登录
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser == null || loginUser.getUserType() == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String userType = loginUser.getUserType().name();

        // 根据路径做 userType 校验
        if (uri.startsWith("/app/")) {
            if (!"APP_USER".equals(userType)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } else if (uri.startsWith("/dev-api/")) {
            if (!"SYS_USER".equals(userType)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        return true;
    }
}