package com.oltocoder.boot.component.trace.config;

import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.trace.SpanProcessor;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@ConfigurationProperties(prefix = "trace")
@Getter
@Setter
public class TraceProperties {
    private boolean enabled = true;

    private Set<String> ignoreSpans;

    //记录跟踪信息到Jaeger
    private  Jaeger jaeger;

    public static class Jaeger extends GrpcProcessor{

        @Override
        protected SpanExporter createExporter() {
            return JaegerGrpcSpanExporter
                    .builder()
                    .setEndpoint(getEndpoint())
                    .setTimeout(getTimeout())
                    .build();
        }
    }

    @Getter
    @Setter
    public abstract static class GrpcProcessor extends BatchProcessor {
        private String endpoint;
        private Duration timeout = Duration.ofSeconds(5);
    }

    @Getter
    @Setter
    public abstract static class BatchProcessor {

        private boolean enabled = true;
        private String endpoint;
        private int maxBatchSize = 2048;
        private int maxQueueSize = 512;
        private Duration exporterTimeout = Duration.ofSeconds(30);
        private Duration scheduleDelay = Duration.ofMillis(100);

        protected abstract SpanExporter createExporter();

        public SpanProcessor create() {
            return BatchSpanProcessor
                    .builder(createExporter())
                    .setScheduleDelay(100, TimeUnit.MILLISECONDS)
                    .setMaxExportBatchSize(maxBatchSize)
                    .setMaxQueueSize(maxQueueSize)
                    .setExporterTimeout(exporterTimeout)
                    .build();
        }
    }
}
