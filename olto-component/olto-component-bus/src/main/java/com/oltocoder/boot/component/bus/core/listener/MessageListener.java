package com.oltocoder.boot.component.bus.core.listener;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import reactor.core.publisher.Mono;

public interface MessageListener {

    Mono<Void> onMessage(RuleData message);
}
