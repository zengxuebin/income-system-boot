package cn.life.income.module.infra.service.db;

import cn.life.income.framework.common.util.object.BeanUtils;
import cn.life.income.framework.mybatis.core.util.JdbcUtils;
import cn.life.income.module.infra.controller.admin.db.vo.DataSourceConfigSaveReqVO;
import cn.life.income.module.infra.dal.dataobject.db.DataSourceConfigDO;
import cn.life.income.module.infra.dal.mysql.db.DataSourceConfigMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.life.income.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.life.income.module.infra.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_EXISTS;
import static cn.life.income.module.infra.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_OK;

/**
 * 数据源配置 Service 实现类
 *
 * @author zengxuebin
 */
@Service
@Validated
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    @Resource
    private DataSourceConfigMapper dataSourceConfigMapper;

    @Override
    public Long createDataSourceConfig(DataSourceConfigSaveReqVO createReqVO) {
        DataSourceConfigDO config = BeanUtils.toBean(createReqVO, DataSourceConfigDO.class);
        validateConnectionOK(config);

        // 插入
        dataSourceConfigMapper.insert(config);
        // 返回
        return config.getId();
    }

    @Override
    public void updateDataSourceConfig(DataSourceConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateDataSourceConfigExists(updateReqVO.getId());
        DataSourceConfigDO updateObj = BeanUtils.toBean(updateReqVO, DataSourceConfigDO.class);
        validateConnectionOK(updateObj);

        // 更新
        dataSourceConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataSourceConfig(Long id) {
        // 校验存在
        validateDataSourceConfigExists(id);
        // 删除
        dataSourceConfigMapper.deleteById(id);
    }

    @Override
    public void deleteDataSourceConfigList(List<Long> ids) {
        dataSourceConfigMapper.deleteByIds(ids);
    }

    private void validateDataSourceConfigExists(Long id) {
        if (dataSourceConfigMapper.selectById(id) == null) {
            throw exception(DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public DataSourceConfigDO getDataSourceConfig(Long id) {
        // 从 DB 中读取
        return dataSourceConfigMapper.selectById(id);
    }

    @Override
    public List<DataSourceConfigDO> getDataSourceConfigList() {
        return dataSourceConfigMapper.selectList();
    }

    private void validateConnectionOK(DataSourceConfigDO config) {
        boolean success = JdbcUtils.isConnectionOK(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw exception(DATA_SOURCE_CONFIG_NOT_OK);
        }
    }

}
