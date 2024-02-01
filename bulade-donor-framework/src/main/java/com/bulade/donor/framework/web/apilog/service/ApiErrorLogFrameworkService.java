package com.bulade.donor.framework.web.apilog.service;

import com.bulade.donor.framework.web.apilog.bo.ApiErrorLogCreateBO;

/**
 * API 错误日志 Framework Service 接口
 */
public interface ApiErrorLogFrameworkService {

    /**
     * 创建 API 错误日志
     */
    void createApiErrorLog(ApiErrorLogCreateBO createBO);
}
