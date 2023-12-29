package com.oltocoder.boot.framework.iot.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * @title: ContextBase
 * @Author cmw
 * @Date: 2023/8/8 16:33
 * @describe
 */
public abstract class ContextBase implements ContextInternal {

    final WorkerPool workerPool;
    final TaskQueue orderedTasks;
    protected ContextBase( WorkerPool workerPool){
        orderedTasks = new TaskQueue();
        this.workerPool=workerPool;
    }

    @Override
    public <T> AsyncFuture<T> executeBlocking(Handler<AsyncPromise<T>> blockingCodeHandler) {

        return executeBlocking(this, blockingCodeHandler, workerPool, orderedTasks);
    }

    static <T> AsyncFuture<T> executeBlocking(ContextInternal context, Handler<AsyncPromise<T>> blockingCodeHandler,
                                         WorkerPool workerPool, TaskQueue queue) {
        PoolMetrics metrics = workerPool.metrics();
        Object queueMetric = metrics != null ? metrics.submitted() : null;
        AsyncPromise<T> promise = context.promise();

        try {
            Runnable command = () -> {
                Object execMetric = null;
                if (metrics != null) {
                    execMetric = metrics.begin(queueMetric);
                }
                context.dispatch(promise, f -> {
                    try {
                        blockingCodeHandler.handle(promise);
                    } catch (Throwable e) {
                        promise.setFailure(e);
                    }
                });
            };
            Executor exec = workerPool.executor();
            if (queue != null) {
                queue.execute(command, exec);
            } else {
                exec.execute(command);
            }
        } catch (RejectedExecutionException e) {
            // Pool is already shut down
            if (metrics != null) {
                metrics.rejected(queueMetric);
            }
            throw e;
        }
        return promise;
    }
}
