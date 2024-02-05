package com.bulade.donor.system.api;

import com.bulade.donor.common.utils.monitor.TracerUtils;
import com.bulade.donor.framework.biz.operatelog.api.OperateLogApi;
import com.bulade.donor.framework.biz.operatelog.bo.OperateLogCreateBO;
import com.bulade.donor.system.service.OperateLogsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * * 操作日志 API 实现类
 */
@Slf4j
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogsService operateLogsService;

    @Override
    public void createOperateLog(OperateLogCreateBO createReqBO) {
        if (Objects.isNull(createReqBO.getTraceId())) {
            createReqBO.setTraceId(TracerUtils.getTraceId());
        }
        //System.err.println("操作日志：" + createReqBO.toString());
        operateLogsService.insert(createReqBO);
    }

}
