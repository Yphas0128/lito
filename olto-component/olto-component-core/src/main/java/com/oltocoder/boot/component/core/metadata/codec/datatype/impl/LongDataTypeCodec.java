package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.LongType;

public class LongDataTypeCodec extends NumberDataTypeCodec<LongType> {
    @Override
    public String getTypeId() {
        return LongType.Identifier;
    }

}
