package com.oltocoder.boot.component.engine.config;

import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.engine.core.engine.DefaultRuleEngine;
import com.oltocoder.boot.component.engine.core.engine.RuleEngine;
import com.oltocoder.boot.component.engine.core.condition.eva.ConditionEvaluator;
import com.oltocoder.boot.component.engine.core.condition.eva.DefaultConditionEvaluator;
import com.oltocoder.boot.component.engine.core.scheduler.IScheduler;
import com.oltocoder.boot.component.engine.core.scheduler.impl.DefaultScheduler;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProviderFactory;
import com.oltocoder.boot.component.engine.core.task.provider.impl.DelayTaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.task.provider.impl.DeviceMessageSendTaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.task.provider.impl.TimerTaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.work.Worker;
import com.oltocoder.boot.component.engine.core.work.impl.DefaultWorker;
import com.oltocoder.boot.module.iot.api.device.DeviceApi;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @title: IOC 注入
 * @Author Ypier
 * @Date: 2023/9/2 14:25
 */
//@AutoConfiguration
public class RuleEngineConfiguration {

    @Bean
    public RuleEngine ruleEngine(IScheduler scheduler){
        return new DefaultRuleEngine(scheduler);
    }

    @Bean
    public ConditionEvaluator conditionEvaluator(){
        return new DefaultConditionEvaluator();
    }

    @Bean
    public DefaultWorker defaultWorker(EventBus eventBus, ConditionEvaluator evaluator){
        return new DefaultWorker("local","local", eventBus,evaluator);
    }

    @Bean
    public IScheduler localScheduler(Worker worker) {
        DefaultScheduler scheduler = new DefaultScheduler("local", worker);
        return scheduler;
    }

    @Bean
    public Scheduler reactorScheduler() {
        return Schedulers.parallel();
    }


    @Bean
    public DeviceMessageSendTaskExecutorProvider deviceMessageSendTaskExecutorProvider(DeviceApi deviceApi){
        return new DeviceMessageSendTaskExecutorProvider(deviceApi);
    }

    @Bean
    public TimerTaskExecutorProvider timerTaskExecutorProvider(){
        return new TimerTaskExecutorProvider();
    }

    @Bean
    public DelayTaskExecutorProvider delayTaskExecutorProvider(Scheduler scheduler){
        return new DelayTaskExecutorProvider(scheduler);
    }

    @Bean
    public BeanPostProcessor autoRegisterStrategy(){
        return new BeanPostProcessor() {

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof TaskExecutorProvider) {
                    TaskExecutorProviderFactory.register(((TaskExecutorProvider) bean));
                }
                return bean;
            }
        };
    }

}
