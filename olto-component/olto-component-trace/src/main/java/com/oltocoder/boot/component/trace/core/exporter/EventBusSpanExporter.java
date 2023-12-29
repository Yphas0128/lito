package com.oltocoder.boot.component.trace.core.exporter;

import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import lombok.AllArgsConstructor;

import java.util.Collection;
@AllArgsConstructor(staticName = "create")
public class EventBusSpanExporter implements SpanExporter {

    private final EventBus eventBus;


    @Override
    public CompletableResultCode export(Collection<SpanData> collection) {
        return null;
    }

    @Override
    public CompletableResultCode flush() {
        return null;
    }

    @Override
    public CompletableResultCode shutdown() {
        return null;
    }
}
