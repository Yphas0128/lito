package com.oltocoder.boot.component.engine.core.task.io;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.bus.core.event.consumer.ConsumerHandler;
import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.engine.core.constants.RuleConstants;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class EventInput implements Input {

    protected final String instanceId;

    protected final String nodeId;

    protected final EventBus eventBus;

//    public Mono<MessageConsumer<RuleData>> consumer() {
//        String topic = String.format(RuleConstants.Topics.input(instanceId, nodeId));
//        return mqEvent.consume(topic);
//    }

    @Override
    public Mono<Void> accept(ConsumerHandler<RuleData> handler) {
        String topic = String.format(RuleConstants.Topics.input(instanceId, nodeId));
        return eventBus.consume(topic,handler);
    }

}
