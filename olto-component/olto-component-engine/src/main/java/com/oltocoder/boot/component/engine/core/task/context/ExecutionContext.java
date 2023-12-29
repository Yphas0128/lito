package com.oltocoder.boot.component.engine.core.task.context;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.task.io.Input;
import com.oltocoder.boot.component.engine.core.task.io.Output;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 规则执行上下文
 */
public interface ExecutionContext {

    /**
     * @return 规则实例ID
     */
    String getInstanceId();

    /**
     * 获取任务信息
     *
     * @return 任务信息
     */
    ScheduleJob getJob();

    RuleData newRuleData(RuleData data);

    RuleData newMapData(Map<String,Object> data);

    Input getInput();

    /**
     * 获取输出接口，用于向下游节点输出数据
     * @return
     */
    Output getOutput();

    <T> Mono<T> fireEvent(String eventId, RuleData ruleData);

    <T> Mono<T> onError(Throwable error, RuleData input);
}
