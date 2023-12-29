package com.oltocoder.boot.component.core.metadata.types;

import com.oltocoder.boot.component.core.metadata.Converter;
import com.oltocoder.boot.component.core.metadata.DataType;

public class StringType extends AbstractDataType<StringType> implements DataType, Converter<String> {
    public static final String Identifier = "string";
    public static final StringType GLOBAL = new StringType();

    @Override
    public String convert(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    @Override
    public Object format(Object value) {
        return String.valueOf(value);
    }

    @Override
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    public String getName() {
        return "字符串";
    }
}
