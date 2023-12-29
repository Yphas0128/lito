package com.oltocoder.boot.component.engine.core.task.io;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public interface Output {

    /**
     *  输出规则数据
     * @param data 规则数据
     * @return bool
     */
    Mono<Boolean> write(Publisher<RuleData> data);

    default Mono<Boolean> write(RuleData data) {
        return write(Mono.just(data));
    }
}
