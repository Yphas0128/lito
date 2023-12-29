package com.oltocoder.boot.component.bus.core.event.consumer;

import io.vertx.core.eventbus.MessageConsumer;

public interface MqConsumer {

    <T> void consume(String topic, ConsumerHandler<T> handler);
}
