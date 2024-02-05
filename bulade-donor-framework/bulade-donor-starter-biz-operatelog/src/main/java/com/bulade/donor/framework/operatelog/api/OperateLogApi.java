package com.bulade.donor.framework.operatelog.api;

import com.bulade.donor.framework.operatelog.bo.OperateLogCreateBO;
import jakarta.validation.Valid;

/**
 * 操作日志 API 接口
 */
public interface OperateLogApi {

    /**
     * 创建操作日志
     *
     * @param createReqDTO 请求
     */
    void createOperateLog(@Valid OperateLogCreateBO createReqDTO);

}
