package com.bulade.donor.infra.api;

import com.bulade.donor.framework.web.apilog.api.ApiErrorLogApi;
import com.bulade.donor.framework.web.apilog.bo.ApiErrorLogCreateBO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

    @Override
    public void createApiErrorLog(ApiErrorLogCreateBO createBO) {
        System.err.println("api 错误日志：" + createBO.toString());
    }

}
