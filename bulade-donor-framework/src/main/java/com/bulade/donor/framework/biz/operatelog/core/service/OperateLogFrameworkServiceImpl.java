package com.bulade.donor.framework.biz.operatelog.core.service;

import cn.hutool.core.bean.BeanUtil;
import com.bulade.donor.framework.biz.operatelog.api.OperateLogApi;
import com.bulade.donor.framework.biz.operatelog.bo.OperateLogCreateBO;
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
        OperateLogCreateBO reqDTO = BeanUtil.toBean(operateLog, OperateLogCreateBO.class);
        operateLogApi.createOperateLog(reqDTO);
    }

}
