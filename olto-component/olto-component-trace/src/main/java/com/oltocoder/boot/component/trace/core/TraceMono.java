package com.oltocoder.boot.component.trace.core;

import com.oltocoder.boot.component.trace.core.consts.TraceHolder;
import com.oltocoder.boot.component.trace.core.span.ReactiveSpan;
import com.oltocoder.boot.component.trace.core.span.ReactiveSpanBuilder;
import io.opentelemetry.api.trace.Tracer;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;
import reactor.function.Consumer3;
import reactor.util.context.ContextView;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class TraceMono<T> extends MonoOperator<T, T> {

    private final Function<ContextView, String> spanName;
    private final Tracer tracer;
    private final Consumer3<ContextView, ReactiveSpan, T> onNext;
    private final Consumer3<ContextView, ReactiveSpan, Long> onComplete;
    private final BiConsumer<ContextView, ReactiveSpanBuilder> onSubscription;
    private final BiConsumer<ContextView, Throwable> onError;
    private final boolean fastSubscribe;
    TraceMono(Mono<? extends T> source,
              Function<ContextView, String> name,
              Tracer tracer,
              Consumer3<ContextView, ReactiveSpan, T> onNext,
              Consumer3<ContextView, ReactiveSpan, Long> onComplete,
              BiConsumer<ContextView, ReactiveSpanBuilder> builderConsumer,
              BiConsumer<ContextView, Throwable> onError,
              boolean fastSubscribe) {
        super(source);
        this.spanName = name == null ? (ctx) -> this.name() : name;
        this.tracer = tracer == null ? TraceHolder.telemetry().getTracer(TraceHolder.appName()) : tracer;
        this.onNext = onNext;
        this.onSubscription = builderConsumer;
        this.onComplete = onComplete;
        this.onError = onError;
        this.fastSubscribe = fastSubscribe;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {

    }
}
