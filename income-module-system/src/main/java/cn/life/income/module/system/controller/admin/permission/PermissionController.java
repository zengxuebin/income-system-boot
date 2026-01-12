package cn.life.income.module.system.controller.admin.permission;

import cn.life.income.framework.common.pojo.CommonResult;
import cn.life.income.module.system.controller.admin.permission.vo.permission.PermissionAssignRoleDataScopeReqVO;
import cn.life.income.module.system.controller.admin.permission.vo.permission.PermissionAssignRoleMenuReqVO;
import cn.life.income.module.system.controller.admin.permission.vo.permission.PermissionAssignUserRoleReqVO;
import cn.life.income.module.system.service.permission.PermissionService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static cn.life.income.framework.common.pojo.CommonResult.success;

/**
 * 权限 Controller，提供赋予用户、角色的权限的 API 接口
 */
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 获得角色拥有的菜单编号
     * @param roleId 角色编号
     * @return 菜单编号集合
     */
    @GetMapping("/list-role-menus")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Set<Long>> getRoleMenuList(Long roleId) {
        return success(permissionService.getRoleMenuListByRoleId(roleId));
    }

    /**
     * 赋予角色菜单
     * @param reqVO 角色菜单请求
     * @return 操作结果
     */
    @PostMapping("/assign-role-menu")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        // 执行菜单的分配
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    /**
     * 赋予角色数据权限
     * @param reqVO 角色数据权限请求
     * @return 操作结果
     */
    @PostMapping("/assign-role-data-scope")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-data-scope')")
    public CommonResult<Boolean> assignRoleDataScope(@Valid @RequestBody PermissionAssignRoleDataScopeReqVO reqVO) {
        permissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return success(true);
    }

    /**
     * 获得管理员拥有的角色编号列表
     * @param userId 用户编号
     * @return 角色编号集合
     */
    @GetMapping("/list-user-roles")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return success(permissionService.getUserRoleIdListByUserId(userId));
    }

    /**
     * 赋予用户角色
     * @param reqVO 用户角色请求
     * @return 操作结果
     */
    @PostMapping("/assign-user-role")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserRole(@Validated @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }
}
