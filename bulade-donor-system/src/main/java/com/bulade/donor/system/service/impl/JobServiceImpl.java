package com.bulade.donor.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.bulade.donor.common.enums.ResultCodeEnum;
import com.bulade.donor.common.utils.collection.CollectionUtils;
import com.bulade.donor.common.utils.object.BeanUtils;
import com.bulade.donor.framework.job.core.enums.JobStatusEnum;
import com.bulade.donor.framework.job.core.scheduler.SchedulerManager;
import com.bulade.donor.framework.job.core.utils.CronUtils;
import com.bulade.donor.system.bo.JobBO;
import com.bulade.donor.system.dao.JobMapper;
import com.bulade.donor.system.model.Job;
import com.bulade.donor.system.service.JobService;
import jakarta.annotation.Resource;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * 定时任务 Service 实现类
 */
@Service
@Validated
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private SchedulerManager schedulerManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createJob(JobBO jobBO) throws SchedulerException {
        validateCronExpression(jobBO.getCronExpression());

        // 校验唯一性
        var info = jobMapper.selectByHandlerName(jobBO.getHandlerName());
        Assert.state(Objects.isNull(info), ResultCodeEnum.JOB_HANDLER_EXISTS.getMessage());

        // 插入
        var job = BeanUtils.toBean(jobBO, Job.class);
        job.setStatus(JobStatusEnum.INIT.getStatus());
        fillJobMonitorTimeoutEmpty(job);
        jobMapper.insert(job);

        // 添加 Job 到 Quartz 中
        schedulerManager.addJob(job.getId(), job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(),
            jobBO.getRetryCount(), jobBO.getRetryInterval());

        // 更新
        var updateObj = Job.builder().id(job.getId()).status(JobStatusEnum.NORMAL.getStatus()).build();
        jobMapper.updateById(updateObj);

        // 返回
        return job.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(JobBO jobBO) throws SchedulerException {
        validateCronExpression(jobBO.getCronExpression());

        // 校验存在
        var job = validateJobExists(jobBO.getId());
        // 只有开启状态，才可以修改.原因是，如果出暂停状态，修改 Quartz Job 时，会导致任务又开始执行
        Assert.state(job.getStatus().equals(JobStatusEnum.NORMAL.getStatus()),
            ResultCodeEnum.JOB_UPDATE_ONLY_NORMAL_STATUS.getMessage());

        // 更新
        var updateObj = BeanUtils.toBean(jobBO, Job.class);
        fillJobMonitorTimeoutEmpty(updateObj);
        jobMapper.updateById(updateObj);

        // 更新 Job 到 Quartz 中
        schedulerManager.updateJob(job.getHandlerName(), jobBO.getHandlerParam(), jobBO.getCronExpression(),
            jobBO.getRetryCount(), jobBO.getRetryInterval());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobStatus(Long id, Integer status) throws SchedulerException {
        // 校验 status

        Assert.state(CollectionUtils.containsAny(status,
                JobStatusEnum.NORMAL.getStatus(), JobStatusEnum.STOP.getStatus()),
            ResultCodeEnum.JOB_CHANGE_STATUS_INVALID.getMessage());

        // 校验存在
        Job job = validateJobExists(id);
        // 校验是否已经为当前状态
        Assert.state(!job.getStatus().equals(status),
            ResultCodeEnum.JOB_CHANGE_STATUS_EQUALS.getMessage());

        // 更新 Job 状态
        var updateObj = Job.builder().id(id).status(status).build();
        jobMapper.updateById(updateObj);

        // 更新状态 Job 到 Quartz 中
        if (JobStatusEnum.NORMAL.getStatus().equals(status)) { // 开启
            schedulerManager.resumeJob(job.getHandlerName());
        } else { // 暂停
            schedulerManager.pauseJob(job.getHandlerName());
        }
    }

    @Override
    public void triggerJob(Long id) throws SchedulerException {
        // 校验存在
        Job job = validateJobExists(id);

        // 触发 Quartz 中的 Job
        schedulerManager.triggerJob(job.getId(), job.getHandlerName(), job.getHandlerParam());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(Long id) throws SchedulerException {
        // 校验存在
        Job job = validateJobExists(id);
        // 更新
        jobMapper.deleteById(id);

        // 删除 Job 到 Quartz 中
        schedulerManager.deleteJob(job.getHandlerName());
    }

    private Job validateJobExists(Long id) {
        var job = jobMapper.selectById(id);
        Assert.notNull(job, ResultCodeEnum.JOB_NOT_EXISTS.getMessage());
        return job;
    }

    private void validateCronExpression(String cronExpression) {
        Assert.state(CronUtils.isValid(cronExpression), ResultCodeEnum.JOB_CRON_EXPRESSION_VALID.getMessage());
    }

    @Override
    public Job getJob(Long id) {
        return jobMapper.selectById(id);
    }

    private static void fillJobMonitorTimeoutEmpty(Job job) {
        if (job.getMonitorTimeout() == null) {
            job.setMonitorTimeout(0);
        }
    }

}
