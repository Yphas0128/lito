package com.oltocoder.boot.framework.iot.concurrent;

/**
 * @title: PoolMetrics
 * @Author cmw
 * @Date: 2023/8/8 16:35
 * @describe
 */
public interface PoolMetrics<T> {
    /**
     * 任务提交
     * A new task has been submitted to access the resource.
     * This method is called from the submitter context.
     *
     * @return the timer measuring the task queuing
     */
    default T submitted() {
        return null;
    }

    /**
     * 任务开始
     * The submitted task start to use the resource.
     *
     * @param t the timer measuring the task queuing returned by {@link #submitted()}
     * @return the timer measuring the task execution
     */
    default T begin(T t) {
        return null;
    }

    /**
     * 记录任务被拒绝执行
     * The task has been rejected. The underlying resource has probably be shutdown.
     *
     * @param t the timer measuring the task queuing returned by {@link #submitted()}
     */
    default void rejected(T t) {
    }

    /**
     * 任务结束
     * The submitted tasks has completed its execution and release the resource.
     *
     * @param succeeded whether or not the task has gracefully completed
     * @param t the timer measuring the task execution returned by {@link #begin}
     */
    default void end(T t, boolean succeeded) {
    }

    default void close() {
    }
}
