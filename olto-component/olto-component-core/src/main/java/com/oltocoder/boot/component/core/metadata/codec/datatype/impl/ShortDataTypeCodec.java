package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.ShortType;

public class ShortDataTypeCodec extends NumberDataTypeCodec<ShortType> {
    @Override
    public String getTypeId() {
        return ShortType.Identifier;
    }
}
