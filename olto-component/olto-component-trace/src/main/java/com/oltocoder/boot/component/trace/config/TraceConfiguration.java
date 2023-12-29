package com.oltocoder.boot.component.trace.config;

import com.oltocoder.boot.component.bus.core.event.mq.EventBus;
import com.oltocoder.boot.component.trace.core.exporter.EventBusSpanExporter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TraceProperties.class)
public class TraceConfiguration {


    @Bean
    public SpanProcessor eventBusSpanExporter(EventBus eventBus) {
        return SimpleSpanProcessor.create(
                EventBusSpanExporter.create(eventBus)
        );
    }

//    @Bean
//    public OpenTelemetry createTelemetry(ObjectProvider<SpanProcessor> spanProcessors,
//                                         TraceProperties traceProperties){
//
//
//
//    }
}
