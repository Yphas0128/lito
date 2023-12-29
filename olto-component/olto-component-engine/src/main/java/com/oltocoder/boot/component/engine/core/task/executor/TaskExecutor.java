package com.oltocoder.boot.component.engine.core.task.executor;

/**
 * @title: 任务执行器,本地具体执行任务的地方
 * @Author Ypier
 * @Date: 2023/9/2 16:35
 */
public interface TaskExecutor {

    /**
     * 任务开始
     */
    void start();

    /**
     * 重新加载
     */
    void reload();
}
