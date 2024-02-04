package com.bulade.donor.system.service.impl;

import com.bulade.donor.system.model.JobLog;
import com.bulade.donor.system.service.JobLogService;

import java.time.LocalDateTime;

/**
 * Job 日志 Service 实现类
 */
public class JobLogServiceImpl implements JobLogService {
    @Override
    public Long createJobLog(Long jobId, LocalDateTime beginTime, String jobHandlerName,
                             String jobHandlerParam, Integer executeIndex) {
        return null;
    }

    @Override
    public void updateJobLogResultAsync(Long logId, LocalDateTime endTime, Integer duration,
                                        boolean success, String result) {

    }

    @Override
    public JobLog getJobLog(Long id) {
        return null;
    }

    @Override
    public Integer cleanJobLog(Integer exceedDay, Integer deleteLimit) {
        return null;
    }
}
