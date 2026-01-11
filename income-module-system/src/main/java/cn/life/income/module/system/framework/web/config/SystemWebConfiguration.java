package cn.life.income.module.system.framework.web.config;

import cn.life.income.framework.swagger.config.IncomeSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的 web 组件的 Configuration
 *
 * @author zengxuebin
 */
@Configuration(proxyBeanMethods = false)
public class SystemWebConfiguration {

    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi systemGroupedOpenApi() {
        return IncomeSwaggerAutoConfiguration.buildGroupedOpenApi("system");
    }

}
