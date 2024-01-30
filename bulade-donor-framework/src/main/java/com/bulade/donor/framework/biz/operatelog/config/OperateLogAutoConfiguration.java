package com.bulade.donor.framework.biz.operatelog.config;


import com.bulade.donor.framework.biz.operatelog.api.OperateLogApi;
import com.bulade.donor.framework.biz.operatelog.core.aop.OperateLogAspect;
import com.bulade.donor.framework.biz.operatelog.core.service.OperateLogFrameworkService;
import com.bulade.donor.framework.biz.operatelog.core.service.OperateLogFrameworkServiceImpl;
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
