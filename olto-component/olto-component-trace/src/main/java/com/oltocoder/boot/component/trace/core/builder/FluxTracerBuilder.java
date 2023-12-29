package com.oltocoder.boot.component.trace.core.builder;

import com.oltocoder.boot.component.trace.core.FluxTracer;
import com.oltocoder.boot.component.trace.core.TraceFlux;
import com.oltocoder.boot.component.trace.core.consts.TraceHolder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class FluxTracerBuilder<T> extends AbstractReactiveTracerBuilder<FluxTracer<T>,T>{

    private boolean fastSubscribe;
    @Override
    public FluxTracer<T> build() {
        return source ->
                new TraceFlux<>(source,
                        spanName,
                        TraceHolder.telemetry().getTracer(scopeName),
                        onNext,
                        onComplete,
                        onSubscription,
                        onError,
                        fastSubscribe);
    }
}
