package cn.life.income.framework.tracer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BizTracer配置类
 *
 * @author zengxuebin
 */
@ConfigurationProperties("income.tracer")
@Data
public class TracerProperties {
}
