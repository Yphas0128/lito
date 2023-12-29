package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.FloatType;

public class FloatDataTypeCodec extends NumberDataTypeCodec<FloatType> {
    @Override
    public String getTypeId() {
        return FloatType.Identifier;
    }
}
