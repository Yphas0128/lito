package com.oltocoder.boot.component.engine.core.task.io;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.core.consts.Reactors;
import com.oltocoder.boot.component.engine.core.constants.RuleConstants;
import com.oltocoder.boot.component.engine.core.scheduler.ScheduleJob;
import com.oltocoder.boot.component.engine.core.condition.eva.ConditionEvaluator;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractOutput implements Output {

    protected final String instanceId;

    private final List<ScheduleJob.Output> outputs;

    private final ConditionEvaluator evaluator;

    private Function<RuleData, Mono<Boolean>> writer;

    public AbstractOutput(String instanceId,
                          List<ScheduleJob.Output> outputs,
                          ConditionEvaluator evaluator) {
        this.instanceId = instanceId;
        this.outputs = outputs;
        this.evaluator = evaluator;
        prepare();
    }

    /**
     * 预处理规则数据输出逻辑
     */
    private void prepare() {

        if(!CollUtil.isEmpty(outputs)){
            List<Function<RuleData, Mono<Boolean>>> writers = new ArrayList<>(outputs.size());
            for (ScheduleJob.Output output : outputs) {
                String topic = createOutputTopic(output.getOutput());

                Function<RuleData, Mono<Boolean>> writer;

                // 是否配备了条件
                if(output.getTermCondition()!= null){
                    Function<RuleData, Mono<Boolean>> condition = evaluator.prepare(output.getTermCondition());
                    writer = data -> condition
                            .apply(data)
                            .flatMap(passed -> {
                                //条件判断返回true才认为成功
                                if (passed) {
                                    return doWrite(topic, data);
                                }
                                return Reactors.ALWAYS_FALSE;
                            });
                }else{
                    writer = (data) -> doWrite(topic, data);
                }
                writers.add(writer);
            }

            // TODO 未完待续
        }else{
            writer = data -> Reactors.ALWAYS_TRUE;
        }
    }

    private String createOutputTopic(String nodeId) {
        // topic "/rule-engine/" + instanceId + "/" + nodeId + "/input"
        return RuleConstants.Topics.input(instanceId, nodeId);
    }

    @Override
    public Mono<Boolean> write(RuleData data) {
        return writer.apply(data);
    }

    @Override
    public Mono<Boolean> write(Publisher<RuleData> data) {
        return Flux.from(data)
                .flatMap(this::write)
                .all(Boolean::booleanValue);
    }

    protected abstract Mono<Boolean> doWrite(String topic, RuleData data);
}
