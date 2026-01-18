package cn.life.income.module.system.controller.admin.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.life.income.framework.common.enums.CommonStatusEnum;
import cn.life.income.framework.common.enums.UserTypeEnum;
import cn.life.income.framework.common.pojo.CommonResult;
import cn.life.income.framework.security.config.SecurityProperties;
import cn.life.income.framework.security.core.util.SecurityFrameworkUtils;
import cn.life.income.module.system.controller.admin.auth.vo.*;
import cn.life.income.module.system.convert.auth.AuthConvert;
import cn.life.income.module.system.dal.dataobject.permission.MenuDO;
import cn.life.income.module.system.dal.dataobject.permission.RoleDO;
import cn.life.income.module.system.dal.dataobject.user.AdminUserDO;
import cn.life.income.module.system.enums.logger.LoginLogTypeEnum;
import cn.life.income.module.system.service.auth.AdminAuthService;
import cn.life.income.module.system.service.permission.MenuService;
import cn.life.income.module.system.service.permission.PermissionService;
import cn.life.income.module.system.service.permission.RoleService;
import cn.life.income.module.system.service.social.SocialClientService;
import cn.life.income.module.system.service.user.AdminUserService;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static cn.life.income.framework.common.exception.enums.GlobalErrorCodeConstants.FORBIDDEN;
import static cn.life.income.framework.common.pojo.CommonResult.success;
import static cn.life.income.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.life.income.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 管理后台 - 认证控制器
 */
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private SocialClientService socialClientService;

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 使用账号密码登录
     */
    @PostMapping("/login")
    @PermitAll
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    /**
     * 登出系统
     */
    @PostMapping("/logout")
    @PermitAll
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    /**
     * 刷新令牌
     */
    @PostMapping("/refresh-token")
    @PermitAll
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    /**
     * 获取登录用户的权限信息
     */
    @GetMapping("/get-permission-info")
    public CommonResult<AuthPermissionInfoRespVO> getPermissionInfo() {
        AdminUserDO user = userService.getUser(getLoginUserId());
        if (user == null) {
            return success(null);
        }

        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(getLoginUserId());
        if (CollUtil.isEmpty(roleIds)) {
            return success(AuthConvert.INSTANCE.convert(user, Collections.emptyList(), Collections.emptyList()));
        }
        List<RoleDO> roles = roleService.getRoleList(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus()));

        Set<Long> menuIds = permissionService.getRoleMenuListByRoleId(convertSet(roles, RoleDO::getId));
        List<MenuDO> menuList = menuService.getMenuList(menuIds);
        menuList = menuService.filterDisableMenus(menuList);

        return success(AuthConvert.INSTANCE.convert(user, roles, menuList));
    }

    /**
     * 注册用户
     */
    @PostMapping("/register")
    @PermitAll
    public CommonResult<AuthLoginRespVO> register(@RequestBody @Valid AuthRegisterReqVO registerReqVO) {
        /// 临时关闭
//        return success(authService.register(registerReqVO));
        return CommonResult.error(FORBIDDEN);
    }

    // ========== 短信登录相关 ==========

    /**
     * 使用短信验证码登录
     */
    @PostMapping("/sms-login")
    @PermitAll
    public CommonResult<AuthLoginRespVO> smsLogin(@RequestBody @Valid AuthSmsLoginReqVO reqVO) {
        return success(authService.smsLogin(reqVO));
    }

    /**
     * 发送手机验证码
     */
    @PostMapping("/send-sms-code")
    @PermitAll
    public CommonResult<Boolean> sendLoginSmsCode(@RequestBody @Valid AuthSmsSendReqVO reqVO) {
        /// 临时关闭
//        authService.sendSmsCode(reqVO);
//        return success(true);
        return CommonResult.error(FORBIDDEN);
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    @PermitAll
    public CommonResult<Boolean> resetPassword(@RequestBody @Valid AuthResetPasswordReqVO reqVO) {
        /// 临时关闭
//        authService.resetPassword(reqVO);
//        return success(true);
        return CommonResult.error(FORBIDDEN);
    }

    // ========== 社交登录相关 ==========

    /**
     * 社交授权的跳转
     */
    @GetMapping("/social-auth-redirect")
    @PermitAll
    public CommonResult<String> socialLogin(@RequestParam("type") Integer type,
                                            @RequestParam("redirectUri") String redirectUri) {
        return success(socialClientService.getAuthorizeUrl(
                type, UserTypeEnum.ADMIN.getValue(), redirectUri));
    }

    /**
     * 社交快捷登录，使用 code 授权码
     */
    @PostMapping("/social-login")
    @PermitAll
    public CommonResult<AuthLoginRespVO> socialQuickLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return success(authService.socialLogin(reqVO));
    }

}
