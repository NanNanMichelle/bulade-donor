package com.bulade.donor.infra.service.logger;

import com.bulade.donor.common.utils.object.BeanUtils;
import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;
import com.bulade.donor.infra.model.logger.ApiErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 错误日志 Service 实现类
 */
@Slf4j
@Service
@Validated
public class ApiErrorLogServiceImpl implements ApiErrorLogService {

    @Override
    public void createApiErrorLog(ApiAccessLogCreateBO createBO) {
        var apiErrorLog = BeanUtils.toBean(createBO, ApiErrorLog.class);
        apiErrorLog.setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
        System.err.println("API 错误日志: " + apiErrorLog);
    }

}
