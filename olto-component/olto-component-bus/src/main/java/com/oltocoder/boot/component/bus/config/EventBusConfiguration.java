package com.oltocoder.boot.component.bus.config;

import com.oltocoder.boot.component.bus.core.broker.SpringMessageBroker;
import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.bus.core.event.consumer.MqConsumer;
import com.oltocoder.boot.component.bus.core.event.consumer.VertxMqConsumer;
import com.oltocoder.boot.component.bus.core.event.mq.DefaultEventBus;
import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.bus.core.event.producer.MqProducer;
import com.oltocoder.boot.component.bus.core.event.producer.VertxMqProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

// todo 需要做到不定义数据类型
public class EventBusConfiguration {

//    @ConditionalOnMissingBean
//    @Bean
//    public MqProducer<RuleData> mqProducer() {
//        return new VertxMqProducer<>(RuleData.class);
//    }
//
//    @ConditionalOnMissingBean
//    @Bean
//    public MqConsumer<RuleData> mqConsumer() {
//        return new VertxMqConsumer<>(RuleData.class);
//    }
//

    @ConditionalOnMissingBean
    @Bean
    public MqProducer mqProducer() {
        return new VertxMqProducer();
    }

    @ConditionalOnMissingBean
    @Bean
    public MqConsumer mqConsumer() {
        return new VertxMqConsumer();
    }
    @Bean
    public DefaultEventBus eventBus(MqConsumer mqConsumer, MqProducer mqProducer){
        return new DefaultEventBus(mqProducer,mqConsumer);
    }
//
//    @Bean
//    public SpringMessageBroker messageBroker(EventBus eventBus){
//        return new SpringMessageBroker(eventBus);
//    }
}
