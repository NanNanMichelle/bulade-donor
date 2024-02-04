package com.bulade.donor.system.service;

import com.bulade.donor.framework.job.core.service.JobLogFrameworkService;
import com.bulade.donor.system.model.JobLog;

/**
 * Job 日志 Service 接口
 */
public interface JobLogService extends JobLogFrameworkService {

    /**
     * 获得定时任务
     *
     * @param id 编号
     * @return 定时任务
     */
    JobLog getJobLog(Long id);


    /**
     * 清理 exceedDay 天前的任务日志
     *
     * @param exceedDay 超过多少天就进行清理
     * @param deleteLimit 清理的间隔条数
     */
    Integer cleanJobLog(Integer exceedDay, Integer deleteLimit);
}
