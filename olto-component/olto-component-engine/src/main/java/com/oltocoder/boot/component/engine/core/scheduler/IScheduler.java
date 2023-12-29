package com.oltocoder.boot.component.engine.core.scheduler;

import com.oltocoder.boot.component.engine.core.task.Task;
import reactor.core.publisher.Flux;

/**
 * @title: 任务调度器
 * @Author Ypier
 * @Date: 2023/9/2 15:56
 */
public interface IScheduler {

    /**
     * 调度任务并返回执行此任务的执行器
     * @param job 任务配置
     * @return 返回执行此任务的执行器
     */
    Flux<Task> schedule(ScheduleJob job);

}
