package com.oltocoder.boot.framework.iot.concurrent;

import java.util.concurrent.ExecutorService;

/**
 * @title: WorkerPool
 * @Author cmw
 * @Date: 2023/8/8 16:35
 * @describe
 */
public class WorkerPool {
    private final ExecutorService pool;
    private final PoolMetrics metrics;

    public WorkerPool(ExecutorService pool, PoolMetrics metrics) {
        this.pool = pool;
        this.metrics = metrics;
    }

    public ExecutorService executor() {
        return pool;
    }

    public PoolMetrics metrics() {
        return metrics;
    }

    void close() {
        if (metrics != null) {
            metrics.close();
        }
        pool.shutdownNow();
    }
}
