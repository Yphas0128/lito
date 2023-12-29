package com.oltocoder.boot.framework.iot.concurrent;

import java.util.concurrent.Executor;

/**
 * @title: WorkerContext
 * @Author cmw
 * @Date: 2023/8/8 16:47
 * @describe
 */
public class WorkerContext extends ContextBase {

    public WorkerContext(WorkerPool workerPool) {
        super(workerPool);
    }

    @Override
    public Executor executor() {
        return null;
    }
}
