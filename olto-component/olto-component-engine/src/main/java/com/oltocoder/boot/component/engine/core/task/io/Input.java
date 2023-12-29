package com.oltocoder.boot.component.engine.core.task.io;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.bus.core.event.consumer.ConsumerHandler;
import reactor.core.publisher.Mono;

public interface Input {

    /**
     * 接受数据
     * @param handler
     * @return
     */
    Mono<Void> accept(ConsumerHandler<RuleData> handler);
}
