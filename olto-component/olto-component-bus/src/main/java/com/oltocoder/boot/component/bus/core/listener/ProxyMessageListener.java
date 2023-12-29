package com.oltocoder.boot.component.bus.core.listener;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

public class ProxyMessageListener implements MessageListener {
    private final Class<?> paramType;
    private final Object target;
    private final Method method;
    @SuppressWarnings("all")
    public ProxyMessageListener(Object target, Method method) {

        this.target = target;
        this.method = method;
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 1) {
            throw new UnsupportedOperationException("unsupported method [" + method + "] parameter");
        }

        if (parameterTypes.length == 1) {
            paramType = parameterTypes[0];
        } else {
            paramType = Void.class;
        }
    }


    @Override
    public Mono<Void> onMessage(RuleData message) {
        return null;
    }
}
