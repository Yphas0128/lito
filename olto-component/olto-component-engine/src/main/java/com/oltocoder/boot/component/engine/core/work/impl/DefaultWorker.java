package com.oltocoder.boot.component.engine.core.work.impl;

import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.task.DefaultTask;
import com.oltocoder.boot.component.engine.core.task.Task;
import com.oltocoder.boot.component.engine.core.task.context.DefaultExecutionContext;
import com.oltocoder.boot.component.engine.core.condition.eva.ConditionEvaluator;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProviderFactory;
import com.oltocoder.boot.component.engine.core.work.Worker;
import lombok.Getter;
import reactor.core.publisher.Mono;

/**
 * @title: DefaultWorker
 * @Author Ypier
 * @Date: 2023/9/2 16:18
 */
public class DefaultWorker implements Worker {

    @Getter
    private final String id;
    @Getter
    private final String name;
    private ConditionEvaluator conditionEvaluator;
    private EventBus eventBus; // vertx eventbus?

    //
    public DefaultWorker(String id,
                         String name,
                         EventBus eventBus,
                         ConditionEvaluator conditionEvaluator) {
        this.id = id;
        this.name = name;
        this.conditionEvaluator = conditionEvaluator;
        this.eventBus = eventBus;
    }

    /**
     * 创建任务
     * @param job 任务
     * @return
     */
    @Override
    public Mono<Task> createTask(ScheduleJob job) {
        return Mono
                .justOrEmpty(TaskExecutorProviderFactory.getTaskExecutorProvider(job.getExecutor()))
                .switchIfEmpty(Mono.error(() -> new UnsupportedOperationException("unsupported executor:" + job.getExecutor())))
                .flatMap(provider-> {
                    DefaultExecutionContext context = createContext(job);
                    return provider
                            .createTask(context)
                            .map(executor -> new DefaultTask(this.getId(), context, executor));
                });
    }

    private DefaultExecutionContext createContext(ScheduleJob job) {
        return new DefaultExecutionContext(getId(), job, eventBus, conditionEvaluator);
    }
}
