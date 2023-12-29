package com.oltocoder.boot.component.core.metadata.codec.datatype;

import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.metadata.codec.datatype.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataTypeCodecsFactory {

    private static final Map<String, DataTypeCodec<? extends DataType>> codecMap = new HashMap<>();

    static {
        register(new BooleanDataTypeCodec());
        register(new ShortDataTypeCodec());
        register(new IntDataTypeCodec());
        register(new LongDataTypeCodec());
        register(new FloatDataTypeCodec());
        register(new DoubleDataTypeCodec());
        register(new EnumDataTypeCodec());
        register(new DateDataTypeCodec());
    }


    public static void register(DataTypeCodec<? extends DataType> codec) {
        System.out.println(codec.getTypeId());
        codecMap.put(codec.getTypeId(), codec);
    }

    public static Optional<DataTypeCodec<DataType>> getCodec(String typeId) {
        System.out.println(typeId);
        return Optional.ofNullable((DataTypeCodec) codecMap.get(typeId));
    }


    public static DataType decode(DataType type, Map<String, Object> config) {
        if (type == null) {
            return null;
        }
        return getCodec(type.getType())
                .map(codec -> codec.decode(type, config))
                .orElse(type);
    }


    public static Optional<Map<String, Object>> encode(DataType type) {
        if (type == null) {
            return Optional.empty();
        }
        return getCodec(type.getType())
                .map(codec -> codec.encode(type));
    }
}
