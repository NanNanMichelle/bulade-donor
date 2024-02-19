package com.bulade.donor.framework.job.config;

import com.bulade.donor.framework.job.core.scheduler.SchedulerManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Optional;

/**
 * 定时任务 Configuration
 */
@AutoConfiguration
@EnableScheduling // 开启 Spring 自带的定时任务
@Slf4j
public class QuartzAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager(Optional<Scheduler> scheduler) {
        return scheduler.map(SchedulerManager::new).orElseGet(() -> new SchedulerManager(null));
    }

}
