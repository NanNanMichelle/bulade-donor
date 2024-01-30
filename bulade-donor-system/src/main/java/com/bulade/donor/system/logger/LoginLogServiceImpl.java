package com.bulade.donor.system.logger;

import com.bulade.donor.common.utils.object.BeanUtils;
import com.bulade.donor.system.bo.LoginLogCreateBO;
import com.bulade.donor.system.model.LoginLog;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


/**
 * 登录日志 Service 实现
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Override
    public void createLoginLog(LoginLogCreateBO reqBO) {
        var loginLog = BeanUtils.toBean(reqBO, LoginLog.class);
        System.err.println(loginLog.toString());
    }

}
