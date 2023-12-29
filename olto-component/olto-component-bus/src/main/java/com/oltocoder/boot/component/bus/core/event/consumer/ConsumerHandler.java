package com.oltocoder.boot.component.bus.core.event.consumer;

public interface ConsumerHandler<T> {

    void handler(T msg);
}
