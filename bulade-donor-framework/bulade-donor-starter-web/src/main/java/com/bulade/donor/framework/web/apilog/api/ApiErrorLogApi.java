package com.bulade.donor.framework.web.apilog.api;

import com.bulade.donor.framework.web.apilog.bo.ApiErrorLogCreateBO;
import jakarta.validation.Valid;

/**
 * API 错误日志的 API 接口
 */
public interface ApiErrorLogApi {

    /**
     * 创建 API 错误日志
     */
    void createApiErrorLog(@Valid ApiErrorLogCreateBO createBO);

}
