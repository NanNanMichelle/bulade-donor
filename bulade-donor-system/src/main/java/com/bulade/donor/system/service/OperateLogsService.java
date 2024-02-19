package com.bulade.donor.system.service;

import com.bulade.donor.framework.operatelog.bo.OperateLogCreateBO;

public interface OperateLogsService {
    Integer insert(OperateLogCreateBO createReqBO);
}
