package com.oltocoder.boot.component.engine.core.task.context;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.core.consts.RuleConstants;
import com.oltocoder.boot.component.core.consts.RuleDataConstants;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.task.io.Input;
import com.oltocoder.boot.component.engine.core.task.io.Output;
import com.oltocoder.boot.framework.common.util.string.StrUtils;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractExecutionContext implements ExecutionContext {

    @Setter
    @Getter
    private ScheduleJob job;

    @Getter
    private final EventBus eventBus;

    @Getter
    private Input input;

    @Getter
    private Output output;

    /**
     *记录数据到RuleData的header中,方便透传到下游数据
     */
    private boolean recordDataToHeader;

    /**
     * 记录数据到RuleData的header中的key
     */
    private String recordDataToHeaderKey = null;

    private final Function<ScheduleJob, Input> inputFactory;
    private final Function<ScheduleJob, Output> outputFactory;

    public AbstractExecutionContext(ScheduleJob job,
                                    EventBus eventBus,
                                    Function<ScheduleJob, Input> inputFactory,
                                    Function<ScheduleJob, Output> outputFactory){
        this.job = job;
        this.eventBus = eventBus;
        this.inputFactory = inputFactory;
        this.outputFactory = outputFactory;

        init();
    }

    private void init() {
        this.input = inputFactory.apply(job);
        this.output = outputFactory.apply(job);

        recordDataToHeader = job
                .getConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER)
                .map(v-> "true".equals(String.valueOf(v))).orElse(false);

        if(recordDataToHeader){
            recordDataToHeaderKey =
                    RuleDataConstants.RECORD_DATA_TO_HEADER_KEY_PREFIX + job
                            .getConfiguration(RuleDataConstants.RECORD_DATA_TO_HEADER_KEY)
                            .map(String::valueOf)
                            .orElse(job.getNodeId());
        }
    }

    @Override
    public <T> Mono<T> fireEvent(String eventId, RuleData data) {
        //规则自定义配置
//        data.setHeader(RuleConstants.Headers.ruleConfiguration, getJob().getRuleConfiguration());
        //任务执行器标识
        data.setHeader(RuleConstants.Headers.jobExecutor, getJob().getExecutor());
        //模型类型
        data.setHeader(RuleConstants.Headers.modelType, getJob().getModelType());

        Mono<T> then = eventBus
                .publish(RuleConstants.Topics.event(job.getInstanceId(), job.getNodeId(), eventId),data)
                .then(Mono.empty());

        if(output != null){
            return output
                    .write(data)
                    .then(then);
        }
        return then;
    }

    @Override
    public RuleData newRuleData(RuleData data) {
        RuleData ruleData = data.newData(data);
        if (recordDataToHeader) {
            ruleData.setHeader(recordDataToHeaderKey, ruleData.getData());
        }
        ruleData.setHeader("sourceNode", getJob().getNodeId());
        return ruleData;
    }

    @Override
    public RuleData newMapData(Map<String, Object> data) {
        RuleData ruleData = RuleData.create(data);
        if (recordDataToHeader) {
            ruleData.setHeader(recordDataToHeaderKey, ruleData.getData());
        }
        ruleData.setHeader("sourceNode", getJob().getNodeId());
        return ruleData;
    }

    @Override
    public String getInstanceId() {
        return job.getInstanceId();
    }

    @Override
    public <T> Mono<T> onError(Throwable error, RuleData sourceData) {
        return fireEvent(RuleConstants.Event.error, createErrorData(error, sourceData));
    }

    private RuleData createErrorData(Throwable e, RuleData source) {
        Map<String, Object> error = new HashMap<>();
        if (e != null) {
            error.put("type", e.getClass().getSimpleName());
            error.put("message", e.getMessage());
            error.put("stack", StrUtils.throwable2String(e));
        }
        Map<String, Object> sourceInfo = new HashMap<>();
        sourceInfo.put("id", getJob().getNodeId());
        sourceInfo.put("type", getJob().getExecutor());
        sourceInfo.put("name", getJob().getName());
        error.put("source", sourceInfo);

        Map<String, Object> value = new HashMap<>();
        value.put(value.containsKey("error") ? "_error" : "error", error);
        if (source != null) {
            source.acceptMap(value::putAll);
            return newRuleData(source.newData(value));
        }else{
            return newMapData(value);
        }
    }
}
