package com.bulade.donor.system.service;

import com.bulade.donor.system.bo.JobBO;
import com.bulade.donor.system.model.Job;
import jakarta.validation.Valid;
import org.quartz.SchedulerException;

/**
 * 定时任务 Service 接口
 */
public interface JobService {

    /**
     * 创建定时任务
     * @return 编号
     */
    Long createJob(@Valid JobBO jobBO) throws SchedulerException;

    /**
     * 更新定时任务
     */
    void updateJob(@Valid JobBO jobBO) throws SchedulerException;

    /**
     * 更新定时任务的状态
     */
    void updateJobStatus(Long id, Integer status) throws SchedulerException;

    /**
     * 触发定时任务
     */
    void triggerJob(Long id) throws SchedulerException;

    /**
     * 删除定时任务
     */
    void deleteJob(Long id) throws SchedulerException;

    /**
     * 获得定时任务
     */
    Job getJob(Long id);

}
