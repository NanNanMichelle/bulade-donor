package com.bulade.donor.framework.web.apilog.api;

import com.bulade.donor.framework.biz.operatelog.bo.OperateLogCreateBO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Override
    public void createApiAccessLog(OperateLogCreateBO createBO) {
        System.err.println(createBO.toString());
    }
}
