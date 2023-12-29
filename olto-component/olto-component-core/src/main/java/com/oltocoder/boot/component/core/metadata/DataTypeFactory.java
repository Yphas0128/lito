package com.oltocoder.boot.component.core.metadata;

import com.oltocoder.boot.component.core.metadata.types.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class DataTypeFactory {

    private final static Map<String, Supplier<DataType>> supports = new ConcurrentHashMap<>();

    static {
        supports.put(BooleanType.Identifier, BooleanType::new);
        supports.put(DateTimeType.Identifier, DateTimeType::new);
        supports.put(DoubleType.Identifier, DoubleType::new);
        supports.put(EnumType.Identifier, EnumType::new);
        supports.put(FloatType.Identifier, FloatType::new);
        supports.put(ShortType.Identifier, ShortType::new);
        supports.put(IntType.Identifier, IntType::new);
        supports.put(LongType.Identifier, LongType::new);

//        supports.put(StringType.Identifier, StringType::new);
    }
    public static Supplier<DataType> lookup(String id) {
        if (id == null) {
            return null;
        }
        return supports.get(id);
    }
}
