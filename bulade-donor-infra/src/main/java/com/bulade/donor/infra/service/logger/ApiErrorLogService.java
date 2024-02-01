package com.bulade.donor.infra.service.logger;

import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;

/**
 * API 访问日志 Service 接口
 */
public interface ApiErrorLogService {

    /**
     * 创建 API 访问日志
     */
    void createApiErrorLog(ApiAccessLogCreateBO createBO);

}
