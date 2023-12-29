package com.oltocoder.boot.component.trace.core.span;
import io.opentelemetry.api.trace.Span;

/**
 * 响应式的span构建器
 * 主要拓展了setAttributeLazy(AttributeKey, Supplier)以提升性能
 */
public interface ReactiveSpan extends Span {
}
