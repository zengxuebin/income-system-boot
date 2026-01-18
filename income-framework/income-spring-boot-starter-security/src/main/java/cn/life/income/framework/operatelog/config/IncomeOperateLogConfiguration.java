package cn.life.income.framework.operatelog.config;

import cn.life.income.framework.operatelog.core.service.LogRecordServiceImpl;
import com.mzt.logapi.service.ILogRecordService;
import com.mzt.logapi.starter.annotation.EnableLogRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 操作日志配置类
 *
 * @author zengxuebin
 */
@EnableLogRecord(tenant = "")
@AutoConfiguration
@Slf4j
public class IncomeOperateLogConfiguration {

    @Bean
    @Primary
    public ILogRecordService iLogRecordServiceImpl() {
        return new LogRecordServiceImpl();
    }

}
