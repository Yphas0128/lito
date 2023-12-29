package com.oltocoder.boot.component.bus.core.event.mq;

import com.oltocoder.boot.component.bus.core.event.consumer.ConsumerHandler;
import reactor.core.publisher.Mono;

public interface EventBus {

    /**
     * @param topic 主题
     * @param handler
     * @return
     */
   <T> Mono<Void> consume(String topic, ConsumerHandler<T> handler);


    /**
     * @param topic 主题
     * @param data  数据
     * @return
     */
    <T> Mono<Boolean> publish(String topic, T data);
}
