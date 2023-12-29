package com.oltocoder.boot.component.engine.core.task;

import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import reactor.core.publisher.Mono;

/**
 * @title: 可运行到任务,对应运行中规则的一个节点。
 * @Author Ypier
 * @Date: 2023/9/2 15:58
 */
public interface Task {

    /**
     * 唯一ID
     *
     * @return ID
     */
    String getId();

    /**
     * @return 任务信息
     */
    ScheduleJob getJob();

    /**
     *开始执行任务
     * @return empty Mono
     */
    Mono<Void> start();
    /**
     * 任务状态
     */
    enum State {
        //运行中
        running,
        //已暂停
        paused,
        //已停止
        shutdown,
        //未知
        unknown
    }
}
