package com.oltocoder.boot.component.engine.core.engine;

import com.oltocoder.boot.component.engine.core.defaults.ScheduleJobCompiler;
import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.scheduler.IScheduler;
import com.oltocoder.boot.component.engine.core.task.Task;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@AllArgsConstructor
public class DefaultRuleEngine implements RuleEngine{

    private final IScheduler IScheduler;

    @Override
    public Flux<Task> startRule(String instanceId, RuleModel model) {
        return Flux.fromIterable(new ScheduleJobCompiler(instanceId, model).compile())
                .flatMap(IScheduler::schedule)
                .collectList()
                .flatMapIterable(Function.identity())
                     .flatMap(task -> task.start().thenReturn(task));
    }
}
