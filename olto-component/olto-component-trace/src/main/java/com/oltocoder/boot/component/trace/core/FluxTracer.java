package com.oltocoder.boot.component.trace.core;

import com.oltocoder.boot.component.trace.core.builder.FluxTracerBuilder;
import com.oltocoder.boot.component.trace.core.span.ReactiveSpan;
import com.oltocoder.boot.component.trace.core.span.ReactiveSpanBuilder;
import reactor.core.publisher.Flux;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import com.oltocoder.boot.component.trace.core.consts.TraceHolder;
public interface FluxTracer<T> extends Function<Flux<T>, Flux<T>> {

    static <T> FluxTracer<T> unsupported() {
        return source -> source;
    }


    static <T> FluxTracer<T> create(String scopeName,
                                    String spanName,
                                    BiConsumer<ReactiveSpan, T> onNext,
                                    BiConsumer<ReactiveSpan, Long> onComplete,
                                    Consumer<ReactiveSpanBuilder> subscription) {
        if (TraceHolder.isDisabled(spanName)) {
            return unsupported();
        }
        return FluxTracerBuilder
                .<T>create(true)
                .spanName(scopeName)
                .onNext(onNext)
                .onComplete(onComplete)
                .onSubscription(subscription)
                .build();
    }
}
