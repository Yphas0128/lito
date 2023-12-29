package com.oltocoder.boot.component.core.consts;

import reactor.core.publisher.Mono;

public interface Reactors {

    Mono<Boolean> ALWAYS_TRUE = Mono.just(true);
    Mono<Boolean> ALWAYS_FALSE = Mono.just(false);
}
