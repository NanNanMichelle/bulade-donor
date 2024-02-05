package com.bulade.donor.system.service.impl;

import com.bulade.donor.framework.biz.operatelog.bo.OperateLogCreateBO;
import com.bulade.donor.system.convert.OperateLogConvert;
import com.bulade.donor.system.dao.OperateLogsMapper;
import com.bulade.donor.system.service.OperateLogsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OperateLogsServiceImpl implements OperateLogsService {

    @Resource
    private OperateLogsMapper operateLogsMapper;

    @Resource
    private OperateLogConvert operateLogConvert;
    @Override
    public Integer insert(OperateLogCreateBO createReqBO) {
        var operateLog = operateLogConvert.convertBoToModel(createReqBO);
        return operateLogsMapper.insert(operateLog);
    }
}
