package com.oltocoder.boot.component.bus.core.event.producer;

public interface MqProducer<T> {

    void publish(String topic, T msg);
}
