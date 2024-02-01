package com.bulade.donor.framework.web.apilog.service;

import cn.hutool.core.bean.BeanUtil;
import com.bulade.donor.framework.biz.operatelog.bo.OperateLogCreateBO;
import com.bulade.donor.framework.web.apilog.api.ApiAccessLogApi;
import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API 访问日志 Framework Service 实现类
 */
@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl implements ApiAccessLogFrameworkService {

    private final ApiAccessLogApi apiAccessLogApi;

    @Override
    @Async
    public void createApiAccessLog(ApiAccessLog apiAccessLog) {
        var reqDTO = BeanUtil.copyProperties(apiAccessLog, ApiAccessLogCreateBO.class);
        apiAccessLogApi.createApiAccessLog(reqDTO);
    }

}
