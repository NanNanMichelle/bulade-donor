package com.bulade.donor.system.service;

import com.bulade.donor.framework.web.apilog.bo.ApiErrorLogCreateBO;

/**
 * API 访问日志 Service 接口
 */
public interface ApiErrorLogService {

    /**
     * 创建 API 访问日志
     */
    void createApiErrorLog(ApiErrorLogCreateBO createBO);

}
