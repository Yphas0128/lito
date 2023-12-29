package com.oltocoder.boot.component.engine.core.task.provider.impl;

import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProvider;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.Map;

public class NotifierTaskExecutorProvider implements TaskExecutorProvider {

    @Getter
    @Setter
    private Map<String, Object> config;


    @Override
    public String getExecutor() {
        return null;
    }

    @Override
    public Mono<TaskExecutor> createTask(ExecutionContext context) {
        return null;
    }
}
