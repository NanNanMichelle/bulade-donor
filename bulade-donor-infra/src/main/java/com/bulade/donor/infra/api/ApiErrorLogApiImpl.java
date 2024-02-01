package com.bulade.donor.infra.api;

import com.bulade.donor.framework.web.apilog.api.ApiErrorLogApi;
import com.bulade.donor.framework.web.apilog.bo.ApiErrorLogCreateBO;
import com.bulade.donor.infra.service.logger.ApiErrorLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateBO createBO) {
        apiErrorLogService.createApiErrorLog(createBO);
    }

}
