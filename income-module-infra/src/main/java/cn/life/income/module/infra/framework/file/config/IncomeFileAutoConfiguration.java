package cn.life.income.module.infra.framework.file.config;

import cn.life.income.module.infra.framework.file.core.client.FileClientFactory;
import cn.life.income.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author zengxuebin
 */
@Configuration(proxyBeanMethods = false)
public class IncomeFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
