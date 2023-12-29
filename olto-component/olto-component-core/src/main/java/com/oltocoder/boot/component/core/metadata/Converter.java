package com.oltocoder.boot.component.core.metadata;

public interface Converter<T> {

    T convert(Object value);
}
