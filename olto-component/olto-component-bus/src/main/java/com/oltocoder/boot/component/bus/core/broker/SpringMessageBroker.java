package com.oltocoder.boot.component.bus.core.broker;

import com.oltocoder.boot.component.bus.core.annotation.Subscribe;
import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.bus.core.listener.ProxyMessageListener;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

@AllArgsConstructor
public class SpringMessageBroker implements BeanPostProcessor {

    private final EventBus eventBus;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> type = ClassUtils.getUserClass(bean);
        ReflectionUtils.doWithMethods(type, method -> {
            AnnotationAttributes subscribes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, Subscribe.class);
            if (CollectionUtils.isEmpty(subscribes)) {
                return;
            }
            String topic = subscribes.getString("topic");
            ProxyMessageListener listener = new ProxyMessageListener(bean, method);
            eventBus.consume(topic, message->{

            });
        });

        return bean;
    }
}
