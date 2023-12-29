package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.metadata.codec.datatype.DataTypeCodec;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public abstract class AbstractDataTypeCodec<T extends DataType> implements DataTypeCodec<T> {

    @Override
    public T decode(T type, Map<String, Object> config) {
        ofNullable(config.get("description"))
                .map(String::valueOf)
                .ifPresent(type::setDescription);

        return type;
    }

    @Override
    public Map<String, Object> encode(T type) {
        Map<String, Object> encoded = new HashMap<>();
        encoded.put("type", getTypeId());
        encoded.put("description", type.getDescription());
        doEncode(encoded, type);
        return encoded;
    }

    protected abstract void doEncode(Map<String, Object> encoded, T type);
}
