package com.bulade.donor.framework.web.apilog.api;

import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;
import jakarta.validation.Valid;

/**
 * API 访问日志的 API 接口
 */
public interface ApiAccessLogApi {

    /**
     * 创建 API 访问日志
     */
    void createApiAccessLog(@Valid ApiAccessLogCreateBO createBO);
}
