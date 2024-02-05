package com.bulade.donor.framework.operatelog.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bulade.donor.framework.operatelog.api.OperateLogApi;
import com.bulade.donor.framework.operatelog.bo.OperateLogCreateBO;
import com.bulade.donor.framework.operatelog.core.service.OperateLog;
import com.bulade.donor.framework.operatelog.core.service.OperateLogFrameworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 实现类
 */
@RequiredArgsConstructor
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        var reqBO = BeanUtil.toBean(operateLog, OperateLogCreateBO.class);
        operateLogApi.createOperateLog(reqBO);
    }

}
