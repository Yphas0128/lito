package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.BooleanType;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import static java.util.Optional.ofNullable;

public class BooleanDataTypeCodec extends AbstractDataTypeCodec<BooleanType> {

    @Override
    public String getTypeId() {
        return BooleanType.Identifier;
    }

    @Override
    public BooleanType decode(BooleanType type, Map<String, Object> config) {
        super.decode(type,config);
        JSONObject jsonObject = new JSONObject(config);

        ofNullable(jsonObject.getString("trueText"))
                .ifPresent(type::setTrueText);
        ofNullable(jsonObject.getString("falseText"))
                .ifPresent(type::setFalseText);
        ofNullable(jsonObject.getString("trueValue"))
                .ifPresent(type::setTrueValue);
        ofNullable(jsonObject.getString("falseValue"))
                .ifPresent(type::setFalseValue);
        ofNullable(jsonObject.getString("description"))
                .ifPresent(type::setDescription);

        return type;
    }

    @Override
    protected void doEncode(Map<String, Object> encoded, BooleanType type) {
        encoded.put("trueText", type.getTrueText());
        encoded.put("falseText", type.getFalseText());
        encoded.put("trueValue", type.getTrueValue());
        encoded.put("falseValue", type.getFalseValue());
    }
}
