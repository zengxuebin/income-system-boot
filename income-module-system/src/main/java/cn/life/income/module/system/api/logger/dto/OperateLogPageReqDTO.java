package cn.life.income.module.system.api.logger.dto;

import cn.life.income.framework.common.pojo.PageParam;
import lombok.Data;

/**
 * 操作日志分页 Request DTO
 *
 * @author zengxuebin
 */
@Data
public class OperateLogPageReqDTO extends PageParam {

    /**
     * 模块类型
     */
    private String type;
    /**
     * 模块数据编号
     */
    private Long bizId;

    /**
     * 用户编号
     */
    private Long userId;

}
