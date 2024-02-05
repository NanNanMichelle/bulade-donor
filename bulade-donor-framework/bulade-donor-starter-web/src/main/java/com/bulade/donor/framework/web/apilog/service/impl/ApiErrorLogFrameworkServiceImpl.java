package com.bulade.donor.framework.web.apilog.service.impl;

import com.bulade.donor.framework.web.apilog.api.ApiErrorLogApi;
import com.bulade.donor.framework.web.apilog.bo.ApiErrorLogCreateBO;
import com.bulade.donor.framework.web.apilog.service.ApiErrorLogFrameworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API 错误日志 Framework Service 实现类
 */
@RequiredArgsConstructor
public class ApiErrorLogFrameworkServiceImpl implements ApiErrorLogFrameworkService {

    private final ApiErrorLogApi apiErrorLogApi;

    @Override
    @Async
    public void createApiErrorLog(ApiErrorLogCreateBO createBO) {
        apiErrorLogApi.createApiErrorLog(createBO);
    }

}
