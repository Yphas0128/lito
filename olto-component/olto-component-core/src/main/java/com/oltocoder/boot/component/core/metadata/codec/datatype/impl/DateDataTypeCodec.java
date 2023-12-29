package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.DateTimeType;
import com.alibaba.fastjson.JSONObject;

import java.time.ZoneId;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class DateDataTypeCodec extends AbstractDataTypeCodec<DateTimeType>{

    @Override
    public String getTypeId() {
        return DateTimeType.Identifier;
    }

    @Override
    public DateTimeType decode(DateTimeType type, Map<String, Object> config) {
        super.decode(type,config);
        JSONObject jsonObject = new JSONObject(config);
        ofNullable(jsonObject.getString("format"))
                .ifPresent(type::setFormat);
        ofNullable(jsonObject.getString("tz"))
                .map(ZoneId::of)
                .ifPresent(type::setZoneId);


        return type;
    }

    @Override
    protected void doEncode(Map<String, Object> encoded, DateTimeType type) {
        encoded.put("format", type.getFormat());
        encoded.put("tz", type.getZoneId().toString());

    }
}
