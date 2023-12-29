package com.oltocoder.boot.component.bus.core.event.producer;

import com.oltocoder.boot.component.bus.core.event.codec.BeanCodec;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

import static com.oltocoder.boot.component.bus.core.manager.VertxManager.mqList;


/**
 * 生产者
 * @param <T>
 */
public class VertxMqProducer<T> implements MqProducer<T>  {

    @Override
    public void publish(String topic, T msg) {
        EventBus eventBus = Vertx.vertx().eventBus();
        // 防止重复注册
        if (!mqList.contains(msg.getClass().getSimpleName())){
            mqList.add(msg.getClass().getSimpleName());
            eventBus.registerCodec(new BeanCodec<>(msg.getClass()));
            eventBus.publish(topic,msg);
        }else{
            eventBus.publish(topic,msg, new DeliveryOptions().setCodecName(msg.getClass().getSimpleName()));
        }

    }
}
