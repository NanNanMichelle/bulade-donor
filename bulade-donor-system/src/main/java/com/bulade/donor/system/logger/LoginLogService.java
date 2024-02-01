package com.bulade.donor.system.logger;

import com.bulade.donor.system.bo.LoginLogCreateBO;
import jakarta.validation.Valid;

/**
 * 登录日志 Service 接口
 */
public interface LoginLogService {

    /**
     * 创建登录日志
     */
    void createLoginLog(@Valid LoginLogCreateBO reqBO);

}
