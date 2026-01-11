package cn.life.income.module.system.api.logger;

import cn.life.income.framework.common.pojo.PageResult;
import cn.life.income.framework.common.util.object.BeanUtils;
import cn.life.income.framework.common.biz.system.logger.dto.OperateLogCreateReqDTO;
import cn.life.income.module.system.api.logger.dto.OperateLogPageReqDTO;
import cn.life.income.module.system.api.logger.dto.OperateLogRespDTO;
import cn.life.income.module.system.dal.dataobject.logger.OperateLogDO;
import cn.life.income.module.system.service.logger.OperateLogService;
import com.fhs.core.trans.anno.TransMethodResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 操作日志 API 实现类
 *
 * @author zengxuebin
 */
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
    }

    @Override
    @TransMethodResult
    public PageResult<OperateLogRespDTO> getOperateLogPage(OperateLogPageReqDTO pageReqDTO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqDTO);
        return BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class);
    }

}
