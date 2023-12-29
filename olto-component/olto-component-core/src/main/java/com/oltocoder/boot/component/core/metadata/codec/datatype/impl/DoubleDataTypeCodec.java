package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.DoubleType;

public class DoubleDataTypeCodec extends NumberDataTypeCodec<DoubleType> {
    @Override
    public String getTypeId() {
        return DoubleType.Identifier;
    }
}
