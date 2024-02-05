package com.bulade.donor.system.api;

import com.bulade.donor.framework.operatelog.api.OperateLogApi;
import com.bulade.donor.framework.operatelog.bo.OperateLogCreateBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * * 操作日志 API 实现类
 */
@Slf4j
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Override
    public void createOperateLog(OperateLogCreateBO createReqBO) {
        System.err.println("操作日志：" + createReqBO.toString());
    }

}
