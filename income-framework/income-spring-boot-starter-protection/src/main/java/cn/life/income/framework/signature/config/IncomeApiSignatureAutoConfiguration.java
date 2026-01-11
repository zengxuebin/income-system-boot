package cn.life.income.framework.signature.config;

import cn.life.income.framework.redis.config.IncomeRedisAutoConfiguration;
import cn.life.income.framework.signature.core.aop.ApiSignatureAspect;
import cn.life.income.framework.signature.core.redis.ApiSignatureRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * HTTP API 签名的自动配置类
 *
 * @author zengxuebin
 */
@AutoConfiguration(after = IncomeRedisAutoConfiguration.class)
public class IncomeApiSignatureAutoConfiguration {

    @Bean
    public ApiSignatureAspect signatureAspect(ApiSignatureRedisDAO signatureRedisDAO) {
        return new ApiSignatureAspect(signatureRedisDAO);
    }

    @Bean
    public ApiSignatureRedisDAO signatureRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new ApiSignatureRedisDAO(stringRedisTemplate);
    }

}
