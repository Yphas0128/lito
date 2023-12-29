package com.oltocoder.boot.component.engine.core.task.provider.impl;

import cn.hutool.core.bean.BeanUtil;
import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.core.consts.RuleConstants;
import com.oltocoder.boot.component.core.util.time.TimeUtils;
import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.AbstractTaskExecutor;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.util.RuleDataUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@AllArgsConstructor
public class DelayTaskExecutorProvider implements TaskExecutorProvider {

    public static final String EXECUTOR = "delay";

    private final Scheduler scheduler;
    @Override
    public String getExecutor() {
        return EXECUTOR;
    }

    @Override
    public Mono<TaskExecutor> createTask(ExecutionContext context) {
        return Mono.just(new DelayTaskExecutor(context, scheduler));
    }
    static class DelayTaskExecutor extends AbstractTaskExecutor {
        private DelayTaskExecutorConfig config;
        private final Scheduler scheduler;
        public DelayTaskExecutor(ExecutionContext context, Scheduler scheduler) {
            super(context);
            this.scheduler = scheduler;
            init();
        }

        void init() {
            config = DelayTaskExecutorConfig.of(context.getJob().getConfiguration());
        }

        @Override
        protected Disposable doStart() {
            if (this.disposable != null) {
                this.disposable.dispose();
            }

            /**
             * 接收到数据
             */
            return context
                    .getInput()
                    .accept(ruleData -> config.create(ruleData,context,scheduler))
                    .subscribe();
//            return config.create(context.getInput().accept(ruleData->{
//
//            }), context, scheduler);
        }

        @Override
        public void reload() {
            init();
            disposable = doStart();
        }
    }

    @Getter
    @Setter
    public static class DelayTaskExecutorConfig  {

        /**
         * 延迟类型
         */
        private PauseType pauseType;

        /**
         * 延迟
         */
        private int timeout;

        /**
         * 延迟时间单位
         */
        private ChronoUnit timeoutUnit;

        //随机延迟从
        private int randomFirst;

        //随机延迟至
        private int randomLast;

        //随机延迟单位
        private ChronoUnit randomUnits;

        //速率 单位时间
        private int nbRateUnits;

        //速率 单位
        private ChronoUnit rateUnits;

        //丢弃被限流的消息时触发错误事件
        private boolean errorOnDrop;

        //速率
        private int rate;

        public DelayTaskExecutorConfig(PauseType pauseType, int timeout, ChronoUnit timeoutUnit) {
            this.pauseType = pauseType;
            this.timeout = timeout;
            this.timeoutUnit = timeoutUnit;
        }

        public static DelayTaskExecutorConfig of(Map<String, Object> configuration) {
            return BeanUtil.toBean(configuration, DelayTaskExecutorConfig.class);
        }

        public Map<String, Object> convertToMap() {
            return BeanUtil.beanToMap(this,false,true);
        }

        public void create(RuleData ruleData, ExecutionContext context, Scheduler scheduler) {
            pauseType.create(this, Flux.just(ruleData),context,scheduler);
        }


        // todo
//        public Disposable create(Mono<MessageConsumer<RuleData>> consumer, ExecutionContext context, Scheduler scheduler) {
//           return consumer.doOnNext(messageConsumer->{
//               messageConsumer.handler(ruleDataMessage -> {
//                   RuleData ruleData = ruleDataMessage.body();
//                   pauseType.create(this,Flux.just(ruleData),context,scheduler);//
//               });
//            }).subscribe();
//        }
    }

    public enum PauseType {
        delayv { /**上游节点指定固定延迟**/
            @Override
            Flux<RuleData> create(DelayTaskExecutorConfig config,
                                  Flux<RuleData> flux,
                                  ExecutionContext context,
                                  Scheduler scheduler) {
                return flux.delayUntil(el->{
                    Map<String, Object> map = RuleDataUtils.toContextMap(el);
                    if (map.get("delay") == null) {
                        return Mono.never();
                    }
                    Duration duration = TimeUtils.parse(String.valueOf(map.get("delay")));
                    return Mono.delay(duration, scheduler);
                });
            }
        },
        delay { /** 固定延迟 **/
            @Override
            Flux<RuleData> create(DelayTaskExecutorConfig config,
                                  Flux<RuleData> flux,
                                  ExecutionContext context,
                                  Scheduler scheduler) {
                return flux.delayUntil(el -> {
                    Duration duration = Duration.of(config.getTimeout(), config.getTimeoutUnit());
                    return Mono.delay(duration, scheduler);
                });
            }
        },
        random { /** 随机 **/
            @Override
            Flux<RuleData> create(DelayTaskExecutorConfig config,
                                  Flux<RuleData> flux,
                                  ExecutionContext context,
                                  Scheduler scheduler) {
                return flux.delayUntil(el->{
                    Duration duration = Duration.of(
                            ThreadLocalRandom.current().nextLong(
                                    config.getRandomFirst(),
                                    config.getRandomLast()),
                                    config.getRandomUnits());
                    return Mono.delay(duration, scheduler);
                });
            }
        },
        rate { /** 速率限制 **/
            @Override
            Flux<RuleData> create(DelayTaskExecutorConfig config, Flux<RuleData> flux, ExecutionContext context, Scheduler scheduler) {
                Duration duration = Duration.of(config.nbRateUnits, config.getRateUnits());
                return flux
                        .window(duration,scheduler)
                        .flatMap(window -> {
                            AtomicLong counter = new AtomicLong();
                            Flux<RuleData> stream;
                            if(config.isErrorOnDrop()){ // 丢弃时触发
                                stream = window
                                        .index()
                                        .flatMap(tp2 -> {
                                            if(tp2.getT1() < config.getRate()) {
                                                return Mono.just(tp2.getT2());
                                            }
                                            return context.fireEvent(RuleConstants.Event.error, context.newRuleData(tp2.getT2()));
                                        });
                            }else{
                                stream = window.take(config.getRate());
                            }

                            return stream.doOnNext(v-> counter.incrementAndGet())
                                    .doOnComplete(()->{
                                        if (counter.get() > 0) {
                                           //    context.getLogger().debug("rate limit execution {}/{}", counter, duration);
                                        }
                                    });
                        },Integer.MAX_VALUE);
            }
        };

        abstract Flux<RuleData> create(DelayTaskExecutorConfig config,
                                       Flux<RuleData> flux,
                                       ExecutionContext context,
                                       Scheduler scheduler);
    }

}
