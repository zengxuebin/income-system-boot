package cn.life.income.module.infra.dal.mysql.db;

import cn.life.income.framework.mybatis.core.mapper.BaseMapperX;
import cn.life.income.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author zengxuebin
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
