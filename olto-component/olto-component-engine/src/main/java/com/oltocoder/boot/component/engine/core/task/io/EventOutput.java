package com.oltocoder.boot.component.engine.core.task.io;


import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.condition.eva.ConditionEvaluator;
import reactor.core.publisher.Mono;

import java.util.List;

public class EventOutput extends AbstractOutput {

    private final EventBus eventBus;

    public EventOutput(String instanceId,
                          EventBus eventBus,
                          List<ScheduleJob.Output> outputs,
                          ConditionEvaluator evaluator) {
        super(instanceId, outputs, evaluator);
        this.eventBus = eventBus;
    }

    @Override
    protected Mono<Boolean> doWrite(String topic, RuleData data) {
       return   eventBus.publish(topic, data);
    }

}
