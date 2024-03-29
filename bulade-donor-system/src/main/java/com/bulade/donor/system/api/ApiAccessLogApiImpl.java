package com.bulade.donor.system.api;

import com.bulade.donor.framework.web.apilog.api.ApiAccessLogApi;
import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;
import com.bulade.donor.system.service.ApiAccessLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateBO createBO) {
        apiAccessLogService.createApiAccessLog(createBO);
    }
}
