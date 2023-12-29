package com.oltocoder.boot.component.bus.core.event.mq;

import com.oltocoder.boot.component.bus.core.event.consumer.ConsumerHandler;
import com.oltocoder.boot.component.bus.core.event.consumer.MqConsumer;
import com.oltocoder.boot.component.bus.core.event.producer.MqProducer;
import reactor.core.publisher.Mono;

public class DefaultEventBus implements EventBus {

    private final MqConsumer mqConsumer;

    private final MqProducer mqProducer;

    public DefaultEventBus(MqProducer mqProducer, MqConsumer mqConsumer){
        this.mqConsumer = mqConsumer;
        this.mqProducer = mqProducer;
    }

    @Override
    public Mono<Void> consume(String topic, ConsumerHandler handler) {
        mqConsumer.consume(topic,handler);
        return Mono.empty();
    }



    @Override
    public <T> Mono<Boolean> publish(String topic, T data) {
        mqProducer.publish(topic, data);
        return Mono.empty();
    }
}
