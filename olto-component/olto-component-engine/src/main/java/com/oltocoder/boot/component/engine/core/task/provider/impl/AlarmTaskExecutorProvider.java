package com.oltocoder.boot.component.engine.core.task.provider.impl;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.FunctionTaskExecutor;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.task.provider.impl.handler.AlarmRuleHandler;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;


@AllArgsConstructor
public class AlarmTaskExecutorProvider implements TaskExecutorProvider {

    public static final String executor = "alarm";

    private final AlarmRuleHandler alarmHandler;

    @Override
    public String getExecutor() {
        return executor;
    }

    @Override
    public Mono<TaskExecutor> createTask(ExecutionContext context) {
        return Mono.just(new AlarmTaskExecutor(context, alarmHandler));
    }

    static class AlarmTaskExecutor extends FunctionTaskExecutor {
        private final AlarmRuleHandler handler;

        public AlarmTaskExecutor(ExecutionContext context, AlarmRuleHandler handler) {
            super("告警", context);
            this.handler = handler;
            reload();
        }
        @Override
        protected Publisher<RuleData> apply(RuleData input) {
            return null;
        }

        @Override
        public void reload() {

        }
    }
}
