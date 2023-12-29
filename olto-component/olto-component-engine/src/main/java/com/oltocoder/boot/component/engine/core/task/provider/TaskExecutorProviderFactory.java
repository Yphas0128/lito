package com.oltocoder.boot.component.engine.core.task.provider;

import java.util.concurrent.ConcurrentHashMap;

public class TaskExecutorProviderFactory {

    private final static ConcurrentHashMap<String, TaskExecutorProvider> map = new ConcurrentHashMap<>();

    public  static TaskExecutorProvider register(TaskExecutorProvider taskExecutorProvider) {
       return map.putIfAbsent(taskExecutorProvider.getExecutor(),taskExecutorProvider);
    }

    public static TaskExecutorProvider getTaskExecutorProvider(String provider){
        return map.get(provider);
    }

}
