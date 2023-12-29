package com.oltocoder.boot.component.engine.core.task.provider;

import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import reactor.core.publisher.Mono;

public interface TaskExecutorProvider {

    /**
     * @return 执行器标识
     */
    String getExecutor();

    /**
     * 创建执行任务
     * @param context 上下文
     * @return 任务
     */
    Mono<TaskExecutor> createTask(ExecutionContext context);
}
