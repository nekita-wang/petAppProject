package com.petlife.platform.web.controller.system;

import java.util.*;

import com.petlife.platform.common.utils.sign.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.petlife.platform.common.constant.Constants;
import com.petlife.platform.common.core.domain.AjaxResult;
import com.petlife.platform.common.core.domain.entity.SysMenu;
import com.petlife.platform.common.core.domain.entity.SysUser;
import com.petlife.platform.common.core.domain.model.LoginBody;
import com.petlife.platform.common.core.domain.model.LoginUser;
import com.petlife.platform.common.core.text.Convert;
import com.petlife.platform.common.utils.DateUtils;
import com.petlife.platform.common.utils.SecurityUtils;
import com.petlife.platform.common.utils.StringUtils;
import com.petlife.platform.framework.web.service.SysLoginService;
import com.petlife.platform.framework.web.service.SysPermissionService;
import com.petlife.platform.framework.web.service.TokenService;
import com.petlife.platform.system.service.ISysConfigService;
import com.petlife.platform.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author pet-life
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 获取公钥 前端用来密码加密
     *
     * @return
     */
    @GetMapping("/publicKey")
    public RsaUtils.RsaKeyPair publicKey() {
        //便于测试 公私秘钥都传了 可改为只传公钥
        RsaUtils.RsaKeyPair rsaKeyPair = new RsaUtils.RsaKeyPair();
        rsaKeyPair.setPublicKey(RsaUtils.getPublicKey());
        return rsaKeyPair;
//        return RsaUtils.rsaKeyPair();
    }


    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        if (!loginUser.getPermissions().equals(permissions))
        {
            loginUser.setPermissions(permissions);
            tokenService.refreshToken(loginUser);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("isDefaultModifyPwd", initPasswordIsModify(user.getPwdUpdateDate()));
        ajax.put("isPasswordExpired", passwordIsExpiration(user.getPwdUpdateDate()));
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
    
    // 检查初始密码是否提醒修改
    public boolean initPasswordIsModify(Date pwdUpdateDate)
    {
        Integer initPasswordModify = Convert.toInt(configService.selectConfigByKey("sys.account.initPasswordModify"));
        return initPasswordModify != null && initPasswordModify == 1 && pwdUpdateDate == null;
    }

    // 检查密码是否过期
    public boolean passwordIsExpiration(Date pwdUpdateDate)
    {
        Integer passwordValidateDays = Convert.toInt(configService.selectConfigByKey("sys.account.passwordValidateDays"));
        if (passwordValidateDays != null && passwordValidateDays > 0)
        {
            if (StringUtils.isNull(pwdUpdateDate))
            {
                // 如果从未修改过初始密码，直接提醒过期
                return true;
            }
            Date nowDate = DateUtils.getNowDate();
            return DateUtils.differentDaysByMillisecond(nowDate, pwdUpdateDate) > passwordValidateDays;
        }
        return false;
    }
}
