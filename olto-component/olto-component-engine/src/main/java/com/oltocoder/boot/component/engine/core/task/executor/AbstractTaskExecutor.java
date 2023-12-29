package com.oltocoder.boot.component.engine.core.task.executor;

import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import lombok.Getter;
import reactor.core.Disposable;

public abstract class AbstractTaskExecutor implements TaskExecutor{

    @Getter
    protected ExecutionContext context;

    protected volatile Disposable disposable;

    public AbstractTaskExecutor(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public void start() {
        if (disposable != null && !disposable.isDisposed()) {
            // TODO 日志记录
            return;
        }
        disposable = doStart();
        // TODO 日志记录
    }

    // 真正开始任务
    protected abstract Disposable doStart();




}
