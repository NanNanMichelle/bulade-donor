package com.bulade.donor.framework.operatelog.config;

import com.bulade.donor.framework.operatelog.api.OperateLogApi;
import com.bulade.donor.framework.operatelog.core.aop.OperateLogAspect;
import com.bulade.donor.framework.operatelog.core.service.OperateLogFrameworkService;
import com.bulade.donor.framework.operatelog.core.service.impl.OperateLogFrameworkServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class OperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
