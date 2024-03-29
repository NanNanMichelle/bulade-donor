package com.bulade.donor.system.service;

import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;

/**
 * API 访问日志 Service 接口
 */
public interface ApiAccessLogService {

    /**
     * 创建 API 访问日志
     */
    void createApiAccessLog(ApiAccessLogCreateBO createBO);

    Integer cleanAccessLog(Integer jobCleanRetainDay, Integer deleteLimit);
}
