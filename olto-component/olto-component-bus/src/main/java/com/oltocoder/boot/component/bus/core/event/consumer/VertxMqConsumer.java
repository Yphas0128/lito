package com.oltocoder.boot.component.bus.core.event.consumer;

import com.oltocoder.boot.component.bus.core.event.codec.BeanCodec;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import org.springframework.core.GenericTypeResolver;

/**
 * 消费者
 */
import static com.oltocoder.boot.component.bus.core.manager.VertxManager.mqList;

public class VertxMqConsumer implements MqConsumer {

    @Override
    public <T> void consume(String topic, ConsumerHandler<T> handler) {
         Class<T> cls = (Class<T>) GenericTypeResolver.resolveTypeArgument(handler.getClass(), ConsumerHandler.class);
        EventBus eventBus = Vertx.vertx().eventBus();
        if (!mqList.contains(cls.getSimpleName())){
            mqList.add(cls.getSimpleName());
            eventBus.registerCodec(new BeanCodec<>(cls));
        }
        eventBus.consumer(topic,  (Handler<Message<T>>) msg -> handler.handler(msg.body()));
    }


}
