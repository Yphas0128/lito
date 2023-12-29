package com.oltocoder.boot.component.trace.core.builder;

import com.oltocoder.boot.component.trace.core.consts.TraceHolder;
import com.oltocoder.boot.component.trace.core.span.ReactiveSpan;
import com.oltocoder.boot.component.trace.core.span.ReactiveSpanBuilder;
import reactor.function.Consumer3;
import reactor.util.context.ContextView;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractReactiveTracerBuilder<T, R> implements ReactiveTracerBuilder<T, R>  {

    String scopeName = TraceHolder.appName();
    Function<ContextView, String> spanName;
    Consumer3<ContextView, ReactiveSpan, R> onNext;
    Consumer3<ContextView, ReactiveSpan, Long> onComplete;
    BiConsumer<ContextView, ReactiveSpanBuilder> onSubscription;
    BiConsumer<ContextView, Throwable> onError;

    @Override
    public ReactiveTracerBuilder<T, R> scopeName(@Nonnull String name) {
        this.scopeName = name;
        return this;
    }

    @Override
    public ReactiveTracerBuilder<T, R> spanName(@Nonnull String name) {
        return this.spanName(ctx-> name);
    }

    @Override
    public ReactiveTracerBuilder<T, R> spanName(@Nonnull Function<ContextView, String> spanName) {
        this.spanName = spanName;
        return this;
    }

    @Override
    public ReactiveTracerBuilder<T, R> onNext(Consumer3<ContextView, ReactiveSpan, R> callback) {
        if (callback == null) {
            return this;
        }

        Consumer3<ContextView, ReactiveSpan, R> that = this.onNext;

        this.onNext = that == null
                ? callback
                :
                (contextView, span, r) -> {
                    that.accept(contextView, span, r);
                    callback.accept(contextView, span, r);
                };
        return this;
    }

    @Override
    public ReactiveTracerBuilder<T, R> onComplete(Consumer3<ContextView, ReactiveSpan, Long> callback) {
        if (callback == null) {
            return this;
        }

        Consumer3<ContextView, ReactiveSpan, Long> that = this.onComplete;

        this.onComplete = that == null
                ?
                callback
                :
                (contextView, span, r) -> {
                    that.accept(contextView, span, r);
                    callback.accept(contextView, span, r);
                };

        return this;
    }

    @Override
    public ReactiveTracerBuilder<T, R> onError(BiConsumer<ContextView, Throwable> callback) {
        if (callback == null) {
            return this;
        }
        this.onError = this.onError == null ? callback : this.onError.andThen(callback);
        return this;
    }

    @Override
    public ReactiveTracerBuilder<T, R> onSubscription(BiConsumer<ContextView, ReactiveSpanBuilder> callback) {
        if (callback == null) {
            return this;
        }
        this.onSubscription = this.onSubscription == null ? callback : this.onSubscription.andThen(callback);
        return this;
    }

    @Override
    public ReactiveTracerBuilder<T, R> onSubscription(Consumer<ReactiveSpanBuilder> callback) {
        return callback == null ? this : onSubscription((contextView, spanBuilder) -> callback.accept(spanBuilder));
    }


    public abstract T build();
}
