package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.NumberType;
import com.oltocoder.boot.component.core.metadata.unit.ValueUnits;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import static java.util.Optional.ofNullable;

public abstract class NumberDataTypeCodec<T extends NumberType<?>> extends AbstractDataTypeCodec<T>{


    @Override
    public T decode(T type, Map<String, Object> config) {
        super.decode(type,config);
        JSONObject jsonObject = new JSONObject(config);
        ofNullable(jsonObject.getDouble("max"))
                .ifPresent(type::setMax);
        ofNullable(jsonObject.getDouble("min"))
                .ifPresent(type::setMin);
        ofNullable(jsonObject.getInteger("scale"))
                .ifPresent(type::setScale);
        ofNullable(jsonObject.getString("unit"))
                .flatMap(ValueUnits::lookup)
                .ifPresent(type::setUnit);
        return type;
    }

    @Override
    protected void doEncode(Map<String, Object> encoded, T type) {
        encoded.put("max", type.getMax());
        encoded.put("min", type.getMin());
        encoded.put("scale", type.getScale());
        if (type.getUnit() != null) {
            encoded.put("unit", type.getUnit().getId());
        }
    }
}
