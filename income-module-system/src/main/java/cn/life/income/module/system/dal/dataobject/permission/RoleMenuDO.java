package cn.life.income.module.system.dal.dataobject.permission;

import cn.life.income.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联
 *
 * @author zengxuebin
 */
@TableName("system_role_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuDO extends BaseDO {

    /**
     * 自增主键
     */
    @TableId
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}
