package com.oltocoder.boot.component.trace.core.consts;

import io.opentelemetry.api.OpenTelemetry;

import java.util.concurrent.atomic.AtomicReference;

public class TraceHolder {

    private static OpenTelemetry telemetry;

    //全局应用名: -Dtrace.app.name
    private static String GLOBAL_APP_NAME = System.getProperty("trace.app.name", "default");

    //是否全局开启: -Dtrace.enabled=true
    private static boolean traceEnabled = Boolean.parseBoolean(System.getProperty("trace.enabled", "true"));
    public static void setupGlobalName(String name) {
        GLOBAL_APP_NAME = name;
    }

    public static String appName() {
        return GLOBAL_APP_NAME;
    }

    public static OpenTelemetry telemetry() {
        return telemetry == null ? OpenTelemetry.noop() : telemetry;
    }

    public static boolean isDisabled(String name) {
        return !isEnabled(name);
    }

    /**
     * 全局开启跟踪
     */
    public static void enable() {
        traceEnabled = true;
    }

    /**
     * 全局关闭跟踪
     */
    public static void disable() {
        traceEnabled = false;
    }

    private static boolean isEnabled(String name) {
        //全局关闭
        if (!traceEnabled) {
            return false;
        }

        AtomicReference<Boolean> enabled = new AtomicReference<>();

        if (enabled.get() == null) {
            return true;
        }

        return enabled.get();
    }
}
