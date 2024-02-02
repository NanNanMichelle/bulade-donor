package com.bulade.donor.system.service.impl;

import com.bulade.donor.common.utils.object.BeanUtils;
import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;
import com.bulade.donor.system.model.ApiAccessLog;
import com.bulade.donor.system.service.ApiAccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 访问日志 Service 实现类
 */
@Slf4j
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Override
    public void createApiAccessLog(ApiAccessLogCreateBO createBO) {
        var apiAccessLog = BeanUtils.toBean(createBO, ApiAccessLog.class);
        System.err.println("API 日志记录:" + apiAccessLog);
    }

}
