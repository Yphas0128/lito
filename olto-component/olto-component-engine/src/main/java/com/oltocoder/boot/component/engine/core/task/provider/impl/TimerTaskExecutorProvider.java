package com.oltocoder.boot.component.engine.core.task.provider.impl;

import cn.hutool.core.bean.BeanUtil;
import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.core.community.TimerSpec;
import com.oltocoder.boot.component.core.consts.RuleConstants;
import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.AbstractTaskExecutor;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProvider;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务执行
 */
public class TimerTaskExecutorProvider implements TaskExecutorProvider {

    public static final String EXECUTOR = "timer";

    @Override
    public String getExecutor() {
        return EXECUTOR;
    }

    @Override
    public Mono<TaskExecutor> createTask(ExecutionContext context) {
        return Mono.just(new TimerTaskExecutor(context));
    }

    static class TimerTaskExecutor extends AbstractTaskExecutor {
        TimerSpec spec;
        public TimerTaskExecutor(ExecutionContext context) {
            super(context);
            spec = convertToSpec(context.getJob().getConfiguration());
        }

        private TimerSpec convertToSpec(Map<String, Object> configuration) {
            return BeanUtil.toBean(configuration,TimerSpec.class);
        }

        @Override
        protected Disposable doStart() {
            return spec
                    .flux()
                    .onBackpressureDrop()/*背压消费不了的就丢弃*/
                    .concatMap(t -> {
                        Map<String, Object> data = new HashMap<>();
                        long currentTime = System.currentTimeMillis();
                        data.put("timestamp", currentTime);
                        data.put("_now", currentTime);
                        data.put("times", t);
                        RuleData ruleData = context.newMapData(data);
                        /**
                         *
                         */
                        return context.getOutput()
                                .write(ruleData)
                                .then(context.fireEvent(RuleConstants.Event.result,ruleData))
                                .onErrorResume(err -> context.onError(err,null).then(Mono.empty()));
                    }).subscribe();
        }

        @Override
        public void reload() {

        }
    }
}
