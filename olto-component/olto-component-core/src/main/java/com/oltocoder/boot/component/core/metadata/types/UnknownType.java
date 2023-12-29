package com.oltocoder.boot.component.core.metadata.types;

import com.oltocoder.boot.component.core.metadata.DataType;

public class UnknownType implements DataType {

    @Override
    public String getIdentifier() {
        return "unknown";
    }

    @Override
    public String getName() {
        return "未知类型";
    }

    @Override
    public String getDescription() {
        return "未知类型";
    }

    @Override
    public Object format(Object value) {
        return String.valueOf(value);
    }
}
