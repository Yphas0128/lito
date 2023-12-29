package com.oltocoder.boot.component.core.metadata.codec.datatype;

import com.oltocoder.boot.component.core.metadata.DataType;

import java.util.Map;

public interface DataTypeCodec<T extends DataType> {

    String getTypeId();

    T decode(T type, Map<String,Object> config);

    Map<String,Object> encode(T type);
}
