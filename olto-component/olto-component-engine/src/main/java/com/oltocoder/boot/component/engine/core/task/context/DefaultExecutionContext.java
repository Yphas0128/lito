package com.oltocoder.boot.component.engine.core.task.context;

import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.condition.eva.ConditionEvaluator;
import com.oltocoder.boot.component.engine.core.task.io.EventInput;
import com.oltocoder.boot.component.engine.core.task.io.EventOutput;

/**
 * @title: DefaultExecutionContext
 * @Author Ypier
 * @Date: 2023/9/2 16:32
 */
public class DefaultExecutionContext extends  AbstractExecutionContext{

    public DefaultExecutionContext(String workerId,
                                   ScheduleJob scheduleJob,
                                   EventBus eventBus,
                                   ConditionEvaluator evaluator){
        super(scheduleJob,
                eventBus,
                job -> new EventInput(job.getInstanceId(), job.getNodeId(), eventBus),
                job -> new EventOutput(job.getInstanceId(), eventBus, job.getOutputs(), evaluator));
    }


}
