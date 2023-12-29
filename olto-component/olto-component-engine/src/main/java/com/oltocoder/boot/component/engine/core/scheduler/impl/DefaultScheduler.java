package com.oltocoder.boot.component.engine.core.scheduler.impl;

import com.oltocoder.boot.component.engine.core.scheduler.IScheduler;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.task.Task;
import com.oltocoder.boot.component.engine.core.work.Worker;
import com.oltocoder.boot.component.engine.core.work.WorkerSelector;
import lombok.Getter;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @title: DefaultScheduler
 * @Author Ypier
 * @Date: 2023/9/2 15:57
 */
public class DefaultScheduler implements IScheduler {

    @Getter
    private WorkerSelector workerSelector = (workers1, rule) -> workers1.take(1);

    private final Worker worker;

    private final Map<String/*规则实例ID*/, Map<String/*nodeId*/, List<Task>>> executors = new ConcurrentHashMap<>();

    private final Map<String, Task> tasks = new ConcurrentHashMap<>();

    public DefaultScheduler(String id , Worker worker){
        this.worker = worker;
    }

    @Override
    public Flux<Task> schedule(ScheduleJob job) {
        return Flux
                .fromIterable(getExecutor(job.getInstanceId(), job.getNodeId()))
                .thenMany(createExecutor(job));
    }

    private Flux<Task> createExecutor(ScheduleJob job) {
      return   Flux.just(worker)
              .flatMap(worker-> worker.createTask(job))
              .doOnNext(this::addTask);
    }

    private void addTask(Task task) {
        tasks.put(task.getId(), task);
        getExecutor(task.getJob().getInstanceId(), task.getJob().getNodeId()).add(task);
    }

//    private Flux<Worker> findWorker(String executor, ScheduleJob job) {
//        return workerSelector.select(Flux.fromIterable(workers.values()))
//    }

    private List<Task> getExecutor(String instanceId, String nodeId) {
        return getExecutor(instanceId).computeIfAbsent(nodeId, ignore -> new CopyOnWriteArrayList<>());
    }

    private Map<String, List<Task>> getExecutor(String instanceId) {
        return executors.computeIfAbsent(instanceId, ignore -> new ConcurrentHashMap<>());
    }

}
