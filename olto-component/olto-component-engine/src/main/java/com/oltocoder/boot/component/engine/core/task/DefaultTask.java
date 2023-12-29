package com.oltocoder.boot.component.engine.core.task;

import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.task.context.AbstractExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @title: DefaultTask
 * @Author Ypier
 * @Date: 2023/9/2 16:33
 */
public class DefaultTask implements Task {

    @Getter
    private final String id;

    private final AbstractExecutionContext context;

    private final TaskExecutor executor;

    /**
     * 任务开始时间
     */
    private long startTime;
    public DefaultTask(String workerId,
                       AbstractExecutionContext context,
                       TaskExecutor executor) {
        this.id = DigestUtils.md5Hex(workerId + ":" + context.getInstanceId() + ":" + context.getJob().getNodeId());
        this.context = context;
        this.executor = executor;
    }

    @Override
    public ScheduleJob getJob() {
        return context.getJob();
    }

    @Override
    public Mono<Void> start() {
        return Mono
                .<Void>fromRunnable(executor::start)
                .doOnSuccess(v-> startTime = System.currentTimeMillis())
                .subscribeOn(Schedulers.boundedElastic()); // 异步执行
    }
}
