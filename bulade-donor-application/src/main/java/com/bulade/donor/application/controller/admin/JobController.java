package com.bulade.donor.application.controller.admin;

import com.bulade.donor.application.payload.convert.JobConvert;
import com.bulade.donor.application.payload.request.JobReq;
import com.bulade.donor.system.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理后台 - 定时任务")
@RestController
@RequestMapping("/job")
@Validated
public class JobController {

    @Resource
    private JobService jobService;

    @PostMapping
    @Operation(summary = "创建定时任务")
    public Long createJob(@Valid @RequestBody JobReq jobReq)
        throws SchedulerException {
        return jobService.createJob(JobConvert.INSTANCE.convertModelToBO(jobReq));
    }
}
