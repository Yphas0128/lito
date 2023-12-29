package com.oltocoder.boot.framework.iot.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * @title: TaskQueue
 * @Author cmw
 * @Date: 2023/8/8 16:37
 * @describe
 */
public class TaskQueue {

    static final Logger log = LoggerFactory.getLogger(TaskQueue.class);

    private static class Task {

        private final Runnable runnable;
        private final Executor exec;

        public Task(Runnable runnable, Executor exec) {
            this.runnable = runnable;
            this.exec = exec;
        }
    }

    // @protectedby tasks
    private final LinkedList<Task> tasks = new LinkedList<>();

    // @protectedby tasks
    private Executor current;

    private final Runnable runner;

    public TaskQueue() {
        runner = this::run;
    }

    private void run() {
        for (; ; ) {
            final Task task;
            //加锁，保证任务队列的操作是线程安全的
            synchronized (tasks) {
                task = tasks.poll();
                //没有任务直接退出死循环
                if (task == null) {
                    current = null;
                    return;
                }
                //如果task的线程与当前线程不是同一个线程
                if (task.exec != current) {
                    //不是则将任务重新添加进tasks
                    tasks.addFirst(task);
                    //并且重新执行run方法
                    task.exec.execute(runner);
                    //设置当前current位当前的线程
                    current = task.exec;
                    return;
                }
            }
            try {
                //是当前线程直接执行
                task.runnable.run();
            } catch (Throwable t) {
                log.error("Caught unexpected Throwable", t);
            }
        }
    };

    /**
     * Run a task.
     *
     * @param task the task to run.
     */
    public void execute(Runnable task, Executor executor) {
        synchronized (tasks) {
            tasks.add(new Task(task, executor));
            if (current == null) {
                current = executor;
                try {
                    executor.execute(runner);
                } catch (RejectedExecutionException e) {
                    current = null;
                    throw e;
                }
            }
        }
    }
}
