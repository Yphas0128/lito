package com.oltocoder.boot.framework.quartz.config;

import com.oltocoder.boot.framework.quartz.core.scheduler.SchedulerManager;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务 Configuration
 */
@AutoConfiguration
@EnableScheduling // 开启 Spring 自带的定时任务
public class OltoQuartzAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager(Scheduler scheduler) {
        return new SchedulerManager(scheduler);
    }

}
