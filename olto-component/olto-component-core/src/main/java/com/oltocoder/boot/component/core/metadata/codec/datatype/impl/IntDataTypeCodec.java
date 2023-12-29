package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.IntType;

public class IntDataTypeCodec extends NumberDataTypeCodec<IntType> {
    @Override
    public String getTypeId() {
        return IntType.Identifier;
    }
}
