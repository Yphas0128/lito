package com.oltocoder.boot.component.engine.core.work;

import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.task.Task;
import reactor.core.publisher.Mono;

/**
 * 工作器,通常是一个服务器节点
 */
public interface Worker {


    /**
     * @return 全局唯一ID
     */
    String getId();

    /**
     * @return 名称
     */
    String getName();

    /**
     * 创建一个Task
     * @param job 任务
     * @return Task
     */
    Mono<Task> createTask(ScheduleJob job);
}
