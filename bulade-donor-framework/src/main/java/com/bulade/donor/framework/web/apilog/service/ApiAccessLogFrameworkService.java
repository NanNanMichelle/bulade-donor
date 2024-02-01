package com.bulade.donor.framework.web.apilog.service;

import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;

/**
 * API 访问日志 Framework Service 接口
 */
public interface ApiAccessLogFrameworkService {

    /**
     * 创建 API 访问日志
     */
    void createApiAccessLog(ApiAccessLogCreateBO createBO);
}
