package cn.life.income.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 *
 * @author zengxuebin
 */
@SpringBootApplication(scanBasePackages = {"${income.info.base-package}.server", "${income.info.base-package}.module"})
public class IncomeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncomeServerApplication.class, args);
    }

}
