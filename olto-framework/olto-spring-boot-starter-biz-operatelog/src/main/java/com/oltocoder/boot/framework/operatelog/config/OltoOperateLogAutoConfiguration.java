package com.oltocoder.boot.framework.operatelog.config;

import com.oltocoder.boot.framework.operatelog.core.aop.OperateLogAspect;
import com.oltocoder.boot.framework.operatelog.core.service.OperateLogFrameworkService;
import com.oltocoder.boot.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.oltocoder.boot.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class OltoOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
